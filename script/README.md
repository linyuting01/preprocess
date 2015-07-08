## log of expt preprocess in smd-16-1.


### pokec

* attribute 2+00(edge_type)+0000(attribute_value)

1. using `pokec_gen_nodes` to generate nodes file **pokec.v** with origin edge file **pokec.e**.
2. using `gpmetis_ve` to partition graph into _k_ part.
3. using `pokec_gen_partitions` to prefetch nodes and generate partitions with edges.
3. using `pokec_fulfil_partitions` to add property nodes into partitions.

----------------------------------------------------------

### yagos3

* nodeID = node_attr: 2+00(edge_type)+0000(attribute_value)

1. download **yagoFacts.tsv**;
2. manually assign node type in edge pattern, store in file **vlabel**
3. using `yago2_map2int` convert graph to numeric graph **yago2.e**, adding node type and recording string-int mapping.
4. cut -f1 gb_vmap -> **yago.v**
5. using `gpmetis_ve` to partition graph into _k_ part.
5. using `yago2_gen_partition` to generate partitions.


stats:
-----highest outgoing degree----
   1638 130000029	<United_States>	place
    807 110020794	
    590 130000853	<United_Kingdom>
    439 110014705	
    426 110009041	<Confederate_States_of_America>
    403 110013480
    390 110018782
    390 100001635

-----highest incoming degree----
 120018 130000029
  27082 130000707
  18617 130000241
  18422 130001409
  14947 130000853
  14230 130002232
  12920 130000031
  12794 130000880
  12363 130002417
  10557 130000130

-----label distribution-----
1166400 other
 500311 person
 197442 website
  58751 place
  30795 drama
  27734 institute
   3675 prize
   3327 airport
   1986 school
   1009 book
    381 currency


