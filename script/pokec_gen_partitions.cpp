#include "iostream"
#include "fstream"
#include "sstream"
#include "map"
#include "set"
#include "queue"
#include "time.h"


using namespace std;

int friends_limit = 300;


bool loadGraph(string filename, map<int, set<int> > &graph, map<int, set<int> > &t2fs) {

	cout<<"friends limit = "<<friends_limit<<endl;

	time_t t_start, t_end;
	t_start = time(NULL) ;

	string line;
	int vid, fid, tid;
	int expt_vertex_size = 0, expt_edge_size = 0;

	/**load vertices into graph*/
	ifstream vfin((filename + ".v").c_str());
	while (getline(vfin, line)) {
		expt_vertex_size++;
		stringstream ss(line);
		ss >> vid;
		set<int> edges;
		graph.insert(pair<int, set<int> >(vid, edges));
		t2fs.insert(pair<int, set<int> >(vid, edges));
	}
	vfin.close();

	/**load edges into graph*/
	ifstream efin((filename + ".e").c_str());
	while (getline(efin, line)) {
		expt_edge_size ++;
		stringstream ss(line);
		ss >> fid >> tid;
		if (graph.at(fid).size() < friends_limit) {
			graph.at(fid).insert(tid);
			t2fs.at(tid).insert(fid);
		}
	}

	int vertex_size = graph.size();
	int edge_size = 0;
	int revert_edge_size = 0;
	for (map<int, set<int> >::iterator it = graph.begin(); it != graph.end(); it++) {
		edge_size += it->second.size();
	}
	for (map<int, set<int> >::iterator it = t2fs.begin(); it != t2fs.end(); it++) {
		revert_edge_size += it->second.size();
	}

	t_end = time(NULL) ;

	printf("loadGraph:: v_size = %d, e_size = %d, time: %.2f s\n", vertex_size, edge_size, difftime(t_end, t_start));
	return vertex_size == expt_vertex_size && edge_size == expt_edge_size && revert_edge_size == expt_edge_size;
}

bool gen_partition(string pfile, int hop, map<int, set<int> > &graph, map<int, set<int> > &t2fs) {

	time_t t_start, t_end;
	t_start = time(NULL) ;

	set<int> partiton_vertices;
	set<int> virtual_vertices;
	map<int, set<int> > v2es;
	map<int, int> v2hop;

	set<int> border_vertices;

	/**load the partition*/
	string line;
	int vid;
	ifstream vfin(pfile.c_str());
	while (getline(vfin, line)) {
		stringstream ss(line);
		ss >> vid;
		// set<int> edges;
		// v2es.insert(pair<int, set<int> >(vid, edges));
		v2hop.insert(pair<int, int>(vid, 0));
		partiton_vertices.insert(vid);
	}
	vfin.close();

	/**process the prefetch*/

	if (hop == 0) {
		for (set<int>::iterator it = partiton_vertices.begin(); it != partiton_vertices.end(); it++) {
			int vid = *it;
			set<int> edges;
			for (set<int>::iterator it2 = graph.at(vid).begin(); it2 != graph.at(vid).end(); it2++) {
				int tid = *it2;
				/**only if the tid is inner node, then add this edge.*/
				if (partiton_vertices.find(tid) != partiton_vertices.end()) {
					edges.insert(tid);
				}
			}
			v2es.insert(pair<int, set<int> >(vid, edges));
		}
	}

	else {
		/** v 2 hop*/
		map<int, int> v2hop;
		set<int> checked;
		queue<int> q;
		for (set<int>::iterator it = partiton_vertices.begin(); it != partiton_vertices.end(); it++) {
			q.push(*it);
			v2hop.insert(pair<int, int>(*it, 0));
		}
		while (!q.empty()) {
			int v = q.front();
			q.pop();
			int next_hop = v2hop.at(v) + 1;
			// printf("processing %d, next_hop = %d, ", v, next_hop);
			if (checked.find(v) == checked.end() && next_hop <= hop) {
				// printf("not checked before.\n");
				set<int> edges;
				for (set<int>::iterator it = graph.at(v).begin(); it != graph.at(v).end(); it++) {
					int tid = *it;
					// printf("tid = %d, ", tid);
					/** add all edges, when the node is not in this partiton, add it as virtual nodes. */
					if (partiton_vertices.find(tid) == partiton_vertices.end()) {
						// printf("is_virtual, ");
						virtual_vertices.insert(tid);
						if (partiton_vertices.find(v) != partiton_vertices.end()) {
							border_vertices.insert(v);
						}
						if (v2hop.find(tid) == v2hop.end()) {
							// printf("add_to map = <%d,%d> ", tid, next_hop);
							v2hop.insert(pair<int, int>(tid, next_hop));
							q.push(tid);
						}
					}
					edges.insert(tid);
				}
				if (v2es.find(v) == v2es.end()) {
					v2es.insert(pair<int, set<int> >(v, edges));
				}
				else {
					for (set<int>::iterator it = edges.begin(); it != edges.end(); it++) {
						v2es.at(v).insert(*it);
					}
				}


				for (set<int>::iterator it = t2fs.at(v).begin(); it != t2fs.at(v).end(); it++) {
					int fid = *it;
					// printf("fid = %d, ", fid);
					/** add all edges, when the node is not in this partiton, add it as virtual nodes. */
					if (partiton_vertices.find(fid) == partiton_vertices.end()) {
						// printf("is_virtual, ");
						virtual_vertices.insert(fid);

						if (partiton_vertices.find(v) != partiton_vertices.end()) {
							border_vertices.insert(v);
						}

						if (v2hop.find(fid) == v2hop.end()) {
							// printf("add_to map = <%d,%d>, ", fid, next_hop);
							v2hop.insert(pair<int, int>(fid, next_hop));
							q.push(fid);
						}
						if (v2es.find(fid) == v2es.end()) {
							// printf("not in v2es map");
							set<int> edges;
							v2es.insert(pair<int, set<int> >(fid, edges));
						}
						v2es.at(fid).insert(v);
					}
					// printf("\n");
				}
				checked.insert(v);
			}
		}
	}

	//TODO: prefetch for larger hop.

	/**output the final results.*/
	ofstream vfout((pfile + ".v").c_str());
	int partition_vertex_size = 0;
	for (set<int>::iterator it = partiton_vertices.begin(); it != partiton_vertices.end(); it++) {
		partition_vertex_size ++;
		vfout << *it << "\t1\t1" << endl;
	}
	int virtual_vertex_size = 0;
	for (set<int>::iterator it = virtual_vertices.begin(); it != virtual_vertices.end(); it++) {
		virtual_vertex_size ++;
		vfout << *it << "\t1\t0" << endl;
	}
	vfout.close();
	cout << "output partiton v_file: " << pfile << ".v" << ", v_size = " << partition_vertex_size + virtual_vertex_size << ", virtual_v_size = " << virtual_vertex_size << endl;

	ofstream efout((pfile + ".e").c_str());
	int edge_size = 0;
	for (map<int, set<int> >::iterator it = v2es.begin(); it != v2es.end(); it++) {
		int fv = it->first;
		for (set<int>::iterator it2 = it->second.begin(); it2 != it->second.end(); it2++) {
			int tv = *it2;
			edge_size ++;
			// string key = fv+"-"+tv;
			// string elabel = e2label.at(pair<int, int>(fv, tv));
			efout << fv << "\t" << tv << "\t1" << endl;
		}
	}
	efout.close();

	t_end = time(NULL) ;
	cout << "output parition e_file:" << pfile << ".e" << ", e_size = " << edge_size << endl;
	cout << "border_vertices_size = " << border_vertices.size() << endl;
	printf("gen_partitions::time: %.2f s\n", difftime(t_end, t_start));
	return true;
}



int main(int argc, char *argv[]) {

	string filename = "./datademo/g1";
	int prefetch = 1;
	int k = 2;
	if (argc < 4) {
		cout << "para: graphfile_basename, prefetch_hop, partition_k" << endl;
	}
	else {
		filename = argv[1];
		stringstream ss1(argv[2]);
		ss1 >> prefetch;
		stringstream ss2(argv[3]);
		ss2 >> k;
	}

	printf("run with graph = %s, prefetch = %d, k = %d\n", filename.c_str(), prefetch, k);

	/**global graph*/
	map<int, set<int> > gg;
	/**a map stores target node 2 source nodes*/
	map<int, set<int> > t2fs;

	bool succ = loadGraph(filename, gg, t2fs);
	cout << "successful load graph = " << succ << endl;

	for (int i = 0; i < k; i++) {
		ostringstream oss;
		oss << filename << ".p" << i;
		string pfile = oss.str();
		cout << "processing partition file" << pfile << endl;
		gen_partition(pfile, prefetch, gg, t2fs);
	}
}
