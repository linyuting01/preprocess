#include "iostream"
#include "fstream"
#include "sstream"
#include "map"
#include "set"
#include "queue"
#include "time.h"

using namespace std;

int main(int argc, char *argv[]) {

	string vfilename, line;
	if (argc < 2) {
		cout << "para: vfilename" << endl;
		return -1;
	}
	vfilename = argv[1];
	map<string, int> nodetype2int;

	int vid;
	string label;
	int int_label;
	int index = 0;
	string x;
	ifstream fin(vfilename.c_str());
	ofstream fout("yago2.v");
	ofstream mapout("yago2_vlabel.map");
	while (getline(fin, line)) {
		stringstream ss(line);
		ss >> vid >> x >> label;
		if (nodetype2int.find(label) == nodetype2int.end()) {
			nodetype2int.insert(pair<string, int>(label, index++));
		}
		int_label = nodetype2int.at(label);
		fout << vid << "\t" << int_label << endl;
	}

	for (map<string, int>::iterator it = nodetype2int.begin(); it != nodetype2int.end(); it++) {
		mapout << it->second << "\t" << it->first << endl;
	}
}