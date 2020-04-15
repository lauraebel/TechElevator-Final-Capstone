import psycopg2
import json

path_to_file = './roles.json'
with open(path_to_file, 'r') as role_file:
    roles = json.load(role_file)

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	for role in roles:
		cur.execute("INSERT INTO roles (name) VALUES (%s)", (role["name"],))
	print("successfully inserted roles");

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")