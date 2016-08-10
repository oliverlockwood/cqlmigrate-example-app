CREATE TABLE secondary_profile (agent TEXT PRIMARY KEY, profile TEXT)
    WITH compaction = { 'class' :  'LeveledCompactionStrategy' };