#include "iostream"
#include "fstream"
#include "sstream"
#include "map"
#include "set"
#include "queue"
#include "time.h"


using namespace std;

void load_inner_nodes(string vfile, set<int> &nodes) {

	/**load vertices into graph*/
	ifstream vfin(vfile.c_str());
	string line;
	int count = 0, vid, label, flag;
	while (getline(vfin, line)) {
		stringstream ss(line);
		ss >> vid >> label >> flag;
		if (flag == 1) {
			//flag=1, inner_node.
		}
		nodes.insert(vid);
	}
	vfin.close();
}

int get_edge_type(int property) {
	return property / 10000 - 200;
}

void append_ve_files(string propfile, string vfile, string efile, set<int> &nodes) {

	//open input file.
	ifstream pfin(propfile.c_str());
	//open efile and append.
	ofstream eout(efile.c_str(), std::fstream::in | std::fstream::out | std::fstream::app);

	set<int> props;

	string line;
	int vid, propnode;
	while (getline(pfin, line)) {
		stringstream ss(line);
		ss >> vid >> propnode;
		if (nodes.find(vid) != nodes.end()) {
			// current partition contains this property.
			if (props.find(propnode) == props.end()) {
				props.insert(propnode);
			}
			eout << vid << "\t" << propnode << "\t" << get_edge_type(propnode)  << endl;
		}
	}
	pfin.close();
	eout.close();

	ofstream vout(vfile.c_str(), std::fstream::in | std::fstream::out | std::fstream::app);
	for (set<int>::iterator it = props.begin(); it != props.end(); it++) {
		vout << *it << "\t" << *it << "\t1" << endl;
	}
	vout.close();
}


int main(int argc, char *argv[]) {

	string graphbase;
	string propfile;
	int k;
	string outputfilename;
	if (argc < 4) {
		cout << "para: graphbase, propfile, partition_k" << endl;
		return -1;
	}
	graphbase = argv[1];
	propfile = argv[2];

	stringstream ss(argv[3]);
	ss >> k;

	for (int i = 0; i < k; i++) {
		cout << "processing partition " << k << endl;
		set<int> nodes;
		stringstream filename1, filename2;
		filename1<<graphbase<<".p"<<i<<".v";
		string par_node_file = filename1.str();
		filename2<<graphbase<<".p"<<i<<".e";
		string par_edge_file = filename2.str();
		load_inner_nodes(par_node_file, nodes );
		append_ve_files(propfile, par_node_file, par_edge_file, nodes);
		cout << "finish processing partition " << k << endl;
	}

	cout << "finished." << endl;
}