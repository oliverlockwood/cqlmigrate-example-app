CREATE TABLE IF NOT EXISTS profile (my_id TEXT PRIMARY KEY, country_code TEXT, entitlements LIST<TEXT>);
CREATE TABLE IF NOT EXISTS roles(role_id INT PRIMARY KEY, role_name TEXT);
CREATE TABLE IF NOT EXISTS graphs(insert_time TIMESTAMP PRIMARY KEY, graph_data TEXT, name TEXT);
ALTER TABLE profile WITH compaction = { 'class' :  'LeveledCompactionStrategy'  };
ALTER TABLE roles WITH compaction = { 'class' :  'LeveledCompactionStrategy'  };
ALTER TABLE graphs WITH compaction = { 'class' :  'LeveledCompactionStrategy'  };
