#include <iostream>
#include <fstream>
#include <set>

using namespace std;


int main(int argc, char *argv[]) {

	string inputfilename;
	string outputfilename;
	if (argc < 3) {
		cout << "para: inputfilename, outputfilename" << endl;
		return -1;
	}
	inputfilename = argv[1];
	outputfilename = argv[2];

	ifstream fin(inputfilename.c_str());
	set<int> nodes;
	int v = 0;
	while (fin >> v) {
		if (nodes.find(v) == nodes.end()) {
			nodes.insert(v);
		}
	}
	fin.close();

	ofstream fout(outputfilename.c_str());
	for (set<int>::iterator it = nodes.begin(); it != nodes.end(); it++) {
		fout << *it << endl;
	}
	fout.close();

	cout << "finished." << endl;
}