DROP TABLE graphs;
CREATE TABLE graphs(insert_time TIMESTAMP, text_key TEXT, graph_data TEXT, name TEXT, PRIMARY KEY (insert_time,text_key));
ALTER TABLE graphs WITH compaction = { 'class' :  'LeveledCompactionStrategy' };
