package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import fileOpe.writefile;

/**
 * this class is used for generating random pattern and data graphs.
 * @author s0944873
 *
 */

public class ranGraph {
	/**
	 * @param args
	 */

	public class grpattr{
		public cg_graph graph ;
		public HashMap<String, String> attr;
	}
	
	public int calculator(int vertexnum, double erate){
		double edgenum;
		edgenum = Math.pow(vertexnum, erate);	// e.g., 1.05, 1.1, 1.2 and so on
		return (int)edgenum;
	}
	
	
	/**
	 * @param vv : node number of the pattern
	 * @param erate : power number of vertex, used for specify edge number
	 * @return : graph and its attribute set
	 */
	public grpattr patternGen(int vv, double erate){
		int ee = this.calculator(vv, erate);
		Vector<String> labelSet = new Vector<String>();
		labelSet.add("A");
		labelSet.add("B");
		labelSet.add("C");
		labelSet.add("D");

		//		for(int k=1; k<2; k=k+2){
		int numOfVertex = vv;
		int numOfEdges = ee;
		cg_graph Graph = new cg_graph();
		HashMap<String, String> attr = new HashMap<String, String>();

		for(int i=0; i<numOfVertex; i++){
			String n = new String();
			n = String.valueOf(i);
			Random randomGenerator = new Random();
			attr.put(n, labelSet.elementAt(randomGenerator.nextInt(4)));
			Graph.addVertex(n);
		}

		for(int j=0; j<numOfEdges; j++){
			String fnode = Graph.getAnode();
			String tnode = Graph.getAnode();
			while(Graph.containsEdge(fnode, tnode)||fnode.equals(tnode)){
				fnode = Graph.getAnode();
				tnode = Graph.getAnode();
			}
			edge e= new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, e);
		}
		//			writefile wf = new writefile();
		//			String path = "/disk/scratch/dataset/random/random-"+numOfVertex+"-"+numOfEdges+".grp";
		//			wf.writeobject(path, Graph);
		//		}
		grpattr ga = new grpattr();
		ga.graph = Graph;
		ga.attr = attr;
		return ga;
	}
	
	
	
	public void patternGen(int vv, int ee, String path){
		Vector<String> labelSet = new Vector<String>();
		labelSet.add("PG");	//	A
		labelSet.add("BA");	//	B
		labelSet.add("SA");	//	C
		labelSet.add("GD");	//	D
		labelSet.add("DBA");	//	E
		
		//		for(int k=1; k<2; k=k+2){
		int numOfVertex = vv;
		int numOfEdges = ee;
		cg_graph Graph = new cg_graph();
		HashMap<String, String> attr = new HashMap<String, String>();

		for(int i=0; i<numOfVertex; i++){
			String n = new String();
			n = String.valueOf(i);
			Random randomGenerator = new Random();
			attr.put(n, labelSet.elementAt(randomGenerator.nextInt(5)));
			Graph.addVertex(n);
		}

		for(int j=0; j<numOfEdges; j++){
			String fnode = Graph.getAnode();
			String tnode = Graph.getAnode();
			while(Graph.containsEdge(fnode, tnode)||fnode.equals(tnode)){
				fnode = Graph.getAnode();
				tnode = Graph.getAnode();
			}
			edge e= new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, e);
		}
		//			writefile wf = new writefile();
		//			String path = "/disk/scratch/dataset/random/random-"+numOfVertex+"-"+numOfEdges+".grp";
		//			wf.writeobject(path, Graph);
		//		}
//		grpattr ga = new grpattr();
//		ga.graph = Graph;
//		ga.attr = attr;
//		return ga;
		writefile wf = new writefile();
		String path1 = path+labelSet.size()+"-Ran-"+vv+"-"+ee+".grp";
		wf.writeobject(path1, Graph);
		String path2 = path+labelSet.size()+"-Ran-"+vv+"-"+ee+".atr";
		wf.writeobject(path2, attr);
	}
	
	public void graphGen(String vv, String ee, String path){

		Vector<String> vSet = new Vector<String>();				//	vSet is used for randomly pick nodes
		HashSet<String> edgeSet = new HashSet<String>();	//	edgeset is used for recording edges, vSet and edgeset are for efficiently detecting duplicate info
		Vector<String> labelSet = new Vector<String>();
		labelSet.add("SA");			//	system architect
		labelSet.add("DBA");		//	database administrator
		labelSet.add("GD");		//	graphic designer
		labelSet.add("BA");		//	business analyst
		labelSet.add("ST");			//	system tester
		labelSet.add("PG");			//	programmer
		labelSet.add("Bio");
		labelSet.add("SE");
		labelSet.add("AI");
		labelSet.add("PM");		//	project manager
		labelSet.add("HR");		//	human resource management
		labelSet.add("MK");		//	marketing
		labelSet.add("FA");			//	finance analyst
		labelSet.add("UD");		//	user interface designer
		labelSet.add("SD");			//	software developer
		

		cg_graph Graph = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>(); 
		Random randomGenerator = new Random();
		for(int i=0; i<Integer.valueOf(vv); i++){
			String n = new String();
			n = String.valueOf(i);
			attrG.put(n, labelSet.elementAt(randomGenerator.nextInt(15)));
			Graph.addVertex(n);
			vSet.add(n);
			System.out.println(Graph.vertexSet().size());
		}

		int vsize = vSet.size();
		String e = "";
		for(int j=0; j<Integer.valueOf(ee); j++){
			String fnode = vSet.elementAt((int)(Math.random()*vsize));
			String tnode = vSet.elementAt((int)(Math.random()*vsize));
			e = fnode+"."+tnode;
			while(edgeSet.contains(e)||fnode.equals(tnode)){
				fnode = vSet.elementAt((int)(Math.random()*vsize));
				e = fnode+"."+tnode;
			}
			edge edge = new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, edge);
			
			edgeSet.add(e);
			System.out.println(Graph.edgeSet().size());
		}

		writefile wf = new writefile();
		String path1 = path+labelSet.size()+"-Ran-"+vv+"-"+ee+".grp";
		wf.writeobject(path1, Graph);
		String path2 = path+labelSet.size()+"-Ran-"+vv+"-"+ee+".atr";
		wf.writeobject(path2, attrG);
	}
	
	
	/**
	 * this procedure generates a graph similar to social network
	 * @param vv
	 * @param ee
	 * @param path
	 */
	public void socialGraphGen(String vv, String ee, String path){

		Vector<String> vSet = new Vector<String>();				//	vSet is used for randomly pick nodes
		HashSet<String> edgeSet = new HashSet<String>();	//	edgeset is used for recording edges, vSet and edgeset are for efficiently detecting duplicate info
		Vector<String> labelSet = new Vector<String>();
		labelSet.add("SA");			//	system architect
		labelSet.add("DBA");		//	database administrator
		labelSet.add("GD");		//	graphic designer
		labelSet.add("BA");		//	business analyst
		labelSet.add("ST");			//	system tester
		labelSet.add("PG");			//	programmer
		labelSet.add("Bio");
		labelSet.add("SE");
		labelSet.add("AI");
		labelSet.add("PM");		//	project manager
		labelSet.add("HR");		//	human resource management
		labelSet.add("MK");		//	marketing
		labelSet.add("FA");			//	finance analyst
		labelSet.add("UD");		//	user interface designer
		labelSet.add("SD");			//	software developer
		

		cg_graph Graph = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>(); 
		Random randomGenerator = new Random();
		for(int i=0; i<Integer.valueOf(vv); i++){
			String n = new String();
			n = String.valueOf(i);
			attrG.put(n, labelSet.elementAt(randomGenerator.nextInt(15)));
			Graph.addVertex(n);
			vSet.add(n);
			System.out.println(Graph.vertexSet().size());
		}

		int vsize = vSet.size();
		String e = "";
		
		/**
		 * bubble sorting
		 */
		for(int i=vsize-1; i>0; i--){
			String temp = "";
			for(int j=0; j<i; j++){
				String v1 = vSet.get(j);
				int v1Deg = Graph.outDegreeOf(v1);
				String v2 = vSet.get(j+1);
				int v2Deg = Graph.outDegreeOf(v2);
				
				if(v2Deg<v1Deg){
					temp = v1;
					vSet.set(j, v2);
					vSet.set(j+1, temp);
				}
			}
		}
		
		
		/**
		 * (a) ranked nodes in ascending order of node degrees; and 
		 * (b) selected a pair of nodes, where one endpoint was picked from last 20% nodes 
		 * with 80% probability and the other one was chosen from the remaining 80% nodes randomly. 
		 */
		int ss = (int) (vsize*0.2);
		Random rm = new Random();
		for(int j=0; j<Integer.valueOf(ee); j++){
			int fvposition = 0;
			int tvposition =  rm.nextInt(vsize-ss);
			int chance = rm.nextInt(10);
			if(chance<8){
				fvposition = (int) (rm.nextInt(ss) + vsize*0.8);
			}
			else if(chance>=8){
				fvposition = rm.nextInt(vsize-ss);
			}
			String fnode = vSet.elementAt(fvposition);			
			String tnode = vSet.elementAt(tvposition);
			e = fnode+"."+tnode;
			while(edgeSet.contains(e)||fnode.equals(tnode)){
				fnode = vSet.elementAt((int)(Math.random()*vsize));
				e = fnode+"."+tnode;
			}
			edge edge = new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, edge);
			
			edgeSet.add(e);
			System.out.println(Graph.edgeSet().size());
		}

		writefile wf = new writefile();
		String path1 = path+labelSet.size()+"-SG-Ran-"+vv+"-"+ee+".grp";
		wf.writeobject(path1, Graph);
		String path2 = path+labelSet.size()+"-SG-Ran-"+vv+"-"+ee+".atr";
		wf.writeobject(path2, attrG);
	}
	
	public void sort(int[] a) {
		int temp = 0;
		for (int i = a.length - 1; i > 0; --i) {
			for (int j = 0; j < i; ++j) {
				if (a[j + 1] < a[j]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
	}

	
	public void graphGen(int vv, double erate){

		int ee = this.calculator(vv, erate);
		Vector<String> vSet = new Vector<String>();			//	vSet is used for randomly pick nodes
		HashSet<String> edgeset = new HashSet<String>();	//	edgeset is used for recording edges, vSet and edgeset are for efficiently detecting duplicate info
		Vector<String> labelSet = new Vector<String>();
		labelSet.add("A");
		labelSet.add("B");
		labelSet.add("C");
		labelSet.add("D");
		labelSet.add("E");
//		labelSet.add("F");
//		labelSet.add("G");
//		labelSet.add("H");
//		labelSet.add("I");
//		labelSet.add("J");

		int numOfVertex = vv;
		int numOfEdges = ee;
		cg_graph Graph = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>(); 
		Random randomGenerator = new Random();
		for(int i=0; i<numOfVertex; i++){
			String n = new String();
			n = String.valueOf(i);
			attrG.put(n, labelSet.elementAt(randomGenerator.nextInt(5)));
			Graph.addVertex(n);
			vSet.add(n);
			System.out.println(Graph.vertexSet().size());
		}

		int vsize = vSet.size();
		String e = "";
		for(int j=0; j<numOfEdges; j++){
			String fnode = vSet.elementAt((int)(Math.random()*vsize));
			String tnode = vSet.elementAt((int)(Math.random()*vsize));
			while(edgeset.contains(e)||fnode.equals(tnode)){
				fnode = vSet.elementAt((int)(Math.random()*vsize));
			}
			edge edge = new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, edge);
			e = fnode+"."+tnode;
			edgeset.add(e);
			System.out.println(Graph.edgeSet().size());
		}

		System.out.println(numOfVertex+" , "+numOfEdges);
//		writefile wf = new writefile();
//		String path = "/disk/scratch/dataset/random/random-"+numOfVertex+"-"+numOfEdges+".grp";
//		wf.writeobject(path, Graph);
	}
	
	
	/**
	 * randomly generate Directed Acyclic Graph
	 * @param vv : size of node
	 * @param ee : size of edge
	 * @return 
	 */
	public grpattr ranDAG(int vv, int ee){
		
		Vector<String> vSet = new Vector<String>();				//	vSet is used for randomly pick nodes
		HashSet<String> edgeset = new HashSet<String>();	//	edgeset is used for recording edges, vSet and edgeset are for efficiently detecting duplicate info
		Vector<String> labelSet = new Vector<String>();
		HashMap<String, Integer> toprank = new HashMap<String, Integer>();
//		labelSet.add("A");
//		labelSet.add("B");
//		labelSet.add("C");
//		labelSet.add("D");
//		labelSet.add("E");

		labelSet.add("SA");			//	system architect
		labelSet.add("DBA");		//	database administrator
		labelSet.add("GD");		//	graphic designer
		labelSet.add("BA");		//	business analyst
		labelSet.add("ST");			//	system tester
		labelSet.add("PG");			//	programmer
//		labelSet.add("Bio");
//		labelSet.add("SE");
//		labelSet.add("AI");
		labelSet.add("PM");		//	project manager
//		labelSet.add("HR");		//	human resource management
		labelSet.add("MK");		//	marketing
//		labelSet.add("FA");			//	finance analyst
//		labelSet.add("UD");		//	user interface designer
//		labelSet.add("SD");			//	software developer
		
		cg_graph Graph = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>(); 
		Random randomGenerator = new Random();
		for(int i=0; i<vv; i++){
			String n = new String();
			n = String.valueOf(i);
			attrG.put(n, labelSet.elementAt(randomGenerator.nextInt(8)));
			Graph.addVertex(n);
			vSet.add(n);
			toprank.put(n, 0);
//			System.out.println(Graph.vertexSet().size());
		}

		
		int vsize = vSet.size();
		String e = "";
		for(int j=0; j<ee; j++){
			int max = Integer.MIN_VALUE;
			String fnode = vSet.elementAt((int)(Math.random()*vsize));
			String tnode = vSet.elementAt((int)(Math.random()*vsize));
			e = fnode+"."+tnode;
			int rf = toprank.get(fnode);
			int rt = toprank.get(tnode);
			while(edgeset.contains(e)||fnode.equals(tnode)||rt>rf){
				fnode = vSet.elementAt((int)(Math.random()*vsize));
				tnode = vSet.elementAt((int)(Math.random()*vsize));
				rf = toprank.get(fnode);
				rt = toprank.get(tnode);
				e = fnode+"."+tnode;
			}
			edge edge = new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, edge);
			
			for(edge eg : Graph.outgoingEdgesOf(fnode)){
				String tn = (String) eg.getTarget();
				int rankt = toprank.get(tn);
				if(max<rankt){
					max = rankt;
				}
			}
			toprank.put(fnode, max+1);
			edgeset.add(e);

			// propagate update of the topological rank
			Queue<String> q = new LinkedList<String>();
			HashSet<String> visited = new HashSet<String>();
			q.add(fnode);
			while(!q.isEmpty()){
				String start = q.poll();
				for(edge eg:Graph.incomingEdgesOf(start)){
					String source = (String) eg.getSource();
					if(!visited.contains(source)){
						int max1 = Integer.MIN_VALUE;
						for(edge eg1 : Graph.outgoingEdgesOf(source)){
							String tn = (String) eg1.getTarget();
							int rankt = toprank.get(tn);
							if(max1<rankt){
								max1 = rankt;
							}
						}
						toprank.put(source, max1+1);
						visited.add(source);
						q.add(source);
					}
				}
			}
		}
		grpattr ga = new grpattr();
		ga.graph = Graph;
		ga.attr = attrG;
		return ga;
	}
	
	/**
	 * this procedure produces dag pattern --- at most 8 nodes for the pattern
	 * @param vv
	 * @param ee
	 * @return
	 */
	public grpattr ranDAGPattern(int vv, int ee){
		
		Vector<String> vSet = new Vector<String>();				//	vSet is used for randomly pick nodes
		HashSet<String> edgeset = new HashSet<String>();	//	edgeset is used for recording edges, vSet and edgeset are for efficiently detecting duplicate info
		Vector<String> labelSet = new Vector<String>();
		HashMap<String, Integer> toprank = new HashMap<String, Integer>();


		labelSet.add("SA");			//	system architect
		labelSet.add("DBA");		//	database administrator
		labelSet.add("GD");		//	graphic designer
		labelSet.add("BA");		//	business analyst
		labelSet.add("ST");			//	system tester
		labelSet.add("PG");			//	programmer
//		labelSet.add("Bio");
//		labelSet.add("SE");
//		labelSet.add("AI");
		labelSet.add("PM");		//	project manager
//		labelSet.add("HR");		//	human resource management
		labelSet.add("MK");		//	marketing
//		labelSet.add("FA");			//	finance analyst
//		labelSet.add("UD");		//	user interface designer
//		labelSet.add("SD");			//	software developer
		
		cg_graph Graph = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>(); 
		Random randomGenerator = new Random();
		for(int i=0; i<vv; i++){
			String n = new String();
			n = String.valueOf(i);
			int pos = randomGenerator.nextInt(labelSet.size());
			attrG.put(n, labelSet.elementAt(pos));
			labelSet.remove(pos);
			Graph.addVertex(n);
			vSet.add(n);
			toprank.put(n, 0);
//			System.out.println(Graph.vertexSet().size());
		}

		
		int vsize = vSet.size();
		String e = "";
		for(int j=0; j<ee; j++){
			int max = Integer.MIN_VALUE;
			String fnode = vSet.elementAt((int)(Math.random()*vsize));
			String tnode = vSet.elementAt((int)(Math.random()*vsize));
			e = fnode+"."+tnode;
			int rf = toprank.get(fnode);
			int rt = toprank.get(tnode);
			while(edgeset.contains(e)||fnode.equals(tnode)||rt>rf){
				fnode = vSet.elementAt((int)(Math.random()*vsize));
				tnode = vSet.elementAt((int)(Math.random()*vsize));
				rf = toprank.get(fnode);
				rt = toprank.get(tnode);
				e = fnode+"."+tnode;
			}
			edge edge = new edge(fnode, tnode);
			Graph.addEdge(fnode, tnode, edge);
			
			for(edge eg : Graph.outgoingEdgesOf(fnode)){
				String tn = (String) eg.getTarget();
				int rankt = toprank.get(tn);
				if(max<rankt){
					max = rankt;
				}
			}
			toprank.put(fnode, max+1);
			edgeset.add(e);

			// propagate update of the topological rank
			Queue<String> q = new LinkedList<String>();
			HashSet<String> visited = new HashSet<String>();
			q.add(fnode);
			while(!q.isEmpty()){
				String start = q.poll();
				for(edge eg:Graph.incomingEdgesOf(start)){
					String source = (String) eg.getSource();
					if(!visited.contains(source)){
						int max1 = Integer.MIN_VALUE;
						for(edge eg1 : Graph.outgoingEdgesOf(source)){
							String tn = (String) eg1.getTarget();
							int rankt = toprank.get(tn);
							if(max1<rankt){
								max1 = rankt;
							}
						}
						toprank.put(source, max1+1);
						visited.add(source);
						q.add(source);
					}
				}
			}
		}
		grpattr ga = new grpattr();
		ga.graph = Graph;
		ga.attr = attrG;
		return ga;
	}
	
	/**
	 * this procedure generates a very simple graph which is used for code testing
	 */
	public grpattr sampleGen(){
		cg_graph g = new cg_graph();
		HashMap<String, String> gattr = new HashMap<String, String>();
		
		String v1 = "1";
		String v2 = "2";
		String v3 = "3";
		String v4 = "4";
		String v5 = "5";
		String v6 = "6";
		String v7 = "7";
		
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addVertex(v6);
		g.addVertex(v7);
		
		gattr.put(v1, "A");
		gattr.put(v2, "B");
		gattr.put(v3, "C");
		gattr.put(v4, "D");
		gattr.put(v5, "D");
		gattr.put(v6, "E");
		gattr.put(v7, "E");
		
		edge e1 = new edge(v1,v2);
		edge e2 = new edge(v1,v3);
		edge e3 = new edge(v2,v4);
		edge e4 = new edge(v4,v6);
		edge e5 = new edge(v3,v5);
		edge e6 = new edge(v5,v7);
		
		g.addEdge(v1, v2, e1);
		g.addEdge(v1, v3, e2);
		g.addEdge(v2, v4, e3);
		g.addEdge(v4, v6, e4);
		g.addEdge(v3, v5, e5);
		g.addEdge(v5, v7, e6);
		
		grpattr ga = new grpattr();
		ga.graph = g;
		ga.attr = gattr;
		return ga;
	}
	
	/**
	 * for sigmod 2014 paper
	 * @return
	 */
	public cg_graph genSample(){
		cg_graph G = new cg_graph();
		HashMap<String, String> attrG = new HashMap<String, String>();
		
		String v1 = "1";
		String v2 = "2";
		String v3 = "3";
		String v4 = "4";
		String v5 = "5";
		String v6 = "6";
		String v7 = "7";
		String v8 = "8";
		String v9 = "9";
		String v10 = "10";
		String v11 = "11";
		
		G.addVertex(v1);
		G.addVertex(v2);
		G.addVertex(v3);
		G.addVertex(v4);
		G.addVertex(v5);
		G.addVertex(v6);
		G.addVertex(v7);
		G.addVertex(v8);
		G.addVertex(v9);
		G.addVertex(v10);
		G.addVertex(v11);
		
		attrG.put(v1, "M");
		attrG.put(v2, "hg1");
		attrG.put(v3, "hg2");
		attrG.put(v4, "hg3");
		attrG.put(v5, "cc1");
		attrG.put(v6, "cc2");
		attrG.put(v7, "cc3");
		attrG.put(v8, "cl1");
		attrG.put(v9, "cl2");
		attrG.put(v10, "cl3");
		attrG.put(v11, "cl4");
		
		edge e1 = new edge(v1, v2);
		edge e2 = new edge(v1, v3);
		edge e3 = new edge(v1, v4);
		edge e4 = new edge(v1, v5);
		edge e5 = new edge(v1, v6);
		edge e6 = new edge(v1, v7);
		edge e7 = new edge(v2, v8);
		edge e8 = new edge(v2, v9);
		edge e9 = new edge(v3, v9);
		edge e10 = new edge(v4, v9);
		edge e11 = new edge(v4, v10);
		edge e12 = new edge(v5, v10);
		edge e13 = new edge(v5, v11);
		edge e14 = new edge(v7, v11);
		edge e15 = new edge(v4, v11);
		
		G.addEdge(v1, v2, e1);
		G.addEdge(v1, v3, e2);
		G.addEdge(v1, v4, e3);
		G.addEdge(v1, v5, e4);
		G.addEdge(v1, v6, e5);
		G.addEdge(v1, v7, e6);
		G.addEdge(v2, v8, e7);
		G.addEdge(v2, v9, e8);
		G.addEdge(v3, v9, e9);
		G.addEdge(v4, v9, e10);
		G.addEdge(v4, v10, e11);
		G.addEdge(v5, v10, e12);
		G.addEdge(v5, v11, e13);
		G.addEdge(v7, v11, e14);
		G.addEdge(v4, v11, e15);
		return G;
	}
	
	public grpattr test(){
		cg_graph g = new cg_graph();
		HashMap<String, String> gattr = new HashMap<String, String>();
		
		String v1 = "1";
		String v2 = "2";
		String v3 = "3";
		String v4 = "4";
//		String v5 = "5";
//		String v6 = "6";
		
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
//		g.addVertex(v5);
//		g.addVertex(v6);
//		g.addVertex(v7);
		
		gattr.put(v1, "A");
		gattr.put(v2, "A");
		gattr.put(v3, "A");
		gattr.put(v4, "A");
//		gattr.put(v5, "B");
//		gattr.put(v6, "C");
//		gattr.put(v7, "E");
		
		edge e1 = new edge(v1,v2);
		edge e2 = new edge(v2,v1);
		edge e3 = new edge(v2,v3);
		edge e4 = new edge(v3,v2);
		edge e5 = new edge(v3,v4);
		edge e6 = new edge(v4,v1);
		
		g.addEdge(v1, v2, e1);
		g.addEdge(v2, v1, e2);
		g.addEdge(v2, v3, e3);
		g.addEdge(v3, v2, e4);
		g.addEdge(v3, v4, e5);
		g.addEdge(v4, v1, e6);
		
		grpattr ga = new grpattr();
		ga.graph = g;
		ga.attr = gattr;
		return ga;
	}
	
	
	public grpattr test1(){
		cg_graph g = new cg_graph();
		HashMap<String, String> gattr = new HashMap<String, String>();
		
		String v1 = "1";
		String v2 = "2";
//		String v3 = "3";
//		String v4 = "4";
//		String v5 = "5";
//		String v6 = "6";
//		String v7 = "7";
		
		g.addVertex(v1);
		g.addVertex(v2);
//		g.addVertex(v3);
//		g.addVertex(v4);
//		g.addVertex(v5);
//		g.addVertex(v6);
//		g.addVertex(v7);
		
		gattr.put(v1, "A");
		gattr.put(v2, "A");
//		gattr.put(v3, "C");
//		gattr.put(v4, "D");
//		gattr.put(v5, "D");
//		gattr.put(v6, "E");
//		gattr.put(v7, "E");
		
		edge e1 = new edge(v1,v2);
		edge e2 = new edge(v2,v1);
//		edge e3 = new edge(v3,v1);
//		edge e4 = new edge(v4,v6);
//		edge e5 = new edge(v3,v5);
//		edge e6 = new edge(v5,v7);
		
		g.addEdge(v1, v2, e1);
		g.addEdge(v2, v1, e2);
//		g.addEdge(v3, v1, e3);
//		g.addEdge(v4, v6, e4);
//		g.addEdge(v3, v5, e5);
//		g.addEdge(v5, v7, e6);
		
		grpattr ga = new grpattr();
		ga.graph = g;
		ga.attr = gattr;
		return ga;
	}
	
	public static void main(String[] args){
		ranGraph rang = new ranGraph();
//		rang.graphGen(args[0], args[1], args[2]);
		rang.graphGen("1000", "10000", "D:/Data/Synthetic/");
		rang.patternGen(4, 6, "D:/Data/Synthetic/");

//		grpattr graph = rang.ranDAG(6, 12);
	}
}