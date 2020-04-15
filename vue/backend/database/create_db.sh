#!/bin/bash
BASEDIR=$(dirname $0)
DATABASE=tool_library
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql" 
#&& psql -U postgres -d $DATABASE -f "$BASEDIR/data.sql"

cd ../../frontend/src/assets/data

python insert_roles.py
python insert_brands.py 
python insert_categories.py
python insert_tools.py