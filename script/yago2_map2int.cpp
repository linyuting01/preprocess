#include "iostream"
#include "fstream"
#include "sstream"
#include "map"
#include "set"
#include "queue"
#include "time.h"

using namespace std;

int getTypeOfNode(int nid, map<int, int> &vlabel_map) {
	if (vlabel_map.find(nid) == vlabel_map.end()) {
		//other -> map defined in file gb_vlabel
		return 11;
	}
	else {
		return vlabel_map.at(nid);
	}
}

int main(int argc, char *argv[]) {

	/**data sample: <id_jqyg1z_zkl_1xi58gi>	<Jefferson_County,_Texas>	<owns>	<Jack_Brooks_Regional_Airport>*/

	string filename = "./datademo/g1";
	string label_pattern, label_map;
	if (argc < 4) {
		cout << "para: graphfilename gb_vlabel, gb_labelmap" << endl;
		return -1;
	}
	filename = argv[1];
	label_pattern = argv[2];
	label_map = argv[3];

	map<string, int> label_int_map;
	map<int, string> rvt_labelmap;
	map<string, string> edgetype_subject;
	map<string, string>	edgetype_object;

	ifstream lmfin(label_map.c_str());
	string line;
	string label_key;
	int label_value;
	while (getline(lmfin, line)) {
		stringstream ss(line);
		ss >> label_value >> label_key;
		cout<<label_value<<","<<label_key<<endl;
		label_int_map.insert(pair<string, int>(label_key, label_value));
		rvt_labelmap.insert(pair<int, string>(label_value, label_key));
	}

	cout<<"label_int_map, size = "<<label_int_map.size()<<endl;

	for (map<string, int>::iterator it = label_int_map.begin(); it != label_int_map.end(); it++) {
		cout << it->first << " -> " << it->second << endl;
	}

	cout<<"======================================="<<endl;

	lmfin.close();

	ifstream lfin(label_pattern.c_str());
	string s1, s2, s3;
	while (getline(lfin, line)) {
		stringstream ss(line);
		ss >> s1 >> s2 >> s3;
		edgetype_subject.insert(pair<string, string>(s3, s1));
		edgetype_object.insert(pair<string, string>(s3, s2));
	}

	for (map<string, string>::iterator it = edgetype_object.begin(); it != edgetype_object.end(); it++) {
		cout << it->first << " -> " << it->second << endl;
	}
	for (map<string, string>::iterator it = edgetype_subject.begin(); it != edgetype_subject.end(); it++) {
		cout << it->first << " -> " << it->second << endl;
	}

	/**global graph*/
	map<string, int> vnum_map;
	map<int, int> vlabel_map;


	map<string, int> elabel_map;

	ifstream fin(filename.c_str());
	string item_id, item_subj, item_pred, item_obj;
	int vlabelindex = 0;
	int elabelindex = 0;

	int subj, pred, obj;
	ofstream fout("yago2e.temp");

	int i = 0;

	while (getline(fin, line)) {
		if (i != 0) {
			stringstream ss(line);
			// cout<<line<<endl;
			ss >> item_id >> item_subj >> item_pred >> item_obj;

			if (item_pred.find("hasGender") != -1) {
				continue;
			}

			if (vnum_map.find(item_subj) == vnum_map.end()) {
				vnum_map.insert(pair<string, int>(item_subj, vlabelindex++));
			}
			subj = vnum_map.at(item_subj);

			if (elabel_map.find(item_pred) == elabel_map.end()) {
				elabel_map.insert(pair<string, int>(item_pred, elabelindex++));
			}
			pred = elabel_map.at(item_pred);

			if (vnum_map.find(item_obj) == vnum_map.end()) {
				vnum_map.insert(pair<string, int>(item_obj, vlabelindex++));
			}
			obj = vnum_map.at(item_obj);
			fout << subj << "\t" << obj << "\t" << pred << endl;

			if (edgetype_subject.find(item_pred) != edgetype_subject.end()) {
				string sub_label = edgetype_subject.at(item_pred);
				if (sub_label != "-" && vlabel_map.find(subj) == vlabel_map.end()) {
					vlabel_map.insert(pair<int, int>(subj, label_int_map.at(sub_label)));
				}
			}
			else {
				cout << "NO_EDGE_KEY??" << item_pred;
			}

			if (edgetype_object.find(item_pred) != edgetype_object.end()) {
				string obj_label = edgetype_object.at(item_pred);
				if (obj_label != "-" && vlabel_map.find(obj) == vlabel_map.end()) {
					vlabel_map.insert(pair<int, int>(obj, label_int_map.at(obj_label)));
				}
			}
			else {
				cout << " ??" << item_pred << endl;
			}
		}
		i++;
	}
	fout.close();

	cout << "replace nodes and add type" << endl;
	ifstream f2in("yago2e.temp");
	ofstream f2out("yago2.e");

	while (getline(f2in, line)) {
		stringstream ss(line);
		ss >> subj >> obj >> pred;
		int tsubj = getTypeOfNode(subj, vlabel_map) * 10000000 + subj;
		int tobj = getTypeOfNode(obj, vlabel_map) * 10000000 + obj;
		f2out << tsubj << "\t" << tobj << "\t" << pred << endl;
	}
	cout << "output e.file" << endl;
	f2out.close();



	cout << "vmap.size =" << vnum_map.size() << endl;

	ofstream vmap_out("gb_vmap");
	for (map<string, int>::iterator it = vnum_map.begin(); it != vnum_map.end(); it++) {
		int type = getTypeOfNode(it->second, vlabel_map);
		int displayID = type * 10000000 + it->second;
		vmap_out << displayID << "\t" << it->first << "\t" << rvt_labelmap.at(type) << endl;
	}


	vmap_out.close();

	ofstream emap_out("gb_emap");
	for (map<string, int>::iterator it = elabel_map.begin(); it != elabel_map.end(); it++) {
		emap_out << it->second << "\t" << it->first << endl;
	}
	emap_out.close();

	cout << "finished. with vlabel_kind = " << vlabelindex << ", elabel_kind = " << elabelindex << endl;
}

