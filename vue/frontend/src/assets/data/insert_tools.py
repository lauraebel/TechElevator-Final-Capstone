import psycopg2
import json

path_to_file = './tools.json'
with open(path_to_file, 'r') as tool_file:
    tools = json.load(tool_file)

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	for tool in tools:
		cur.execute("INSERT INTO tools (name, description, img_name, brand_id) VALUES (%s, %s, %s, %s) RETURNING id", (tool['toolName'], tool['toolDescription'], tool['toolImgName'], tool['toolBrand'],))
		toolID = cur.fetchone()
		for category in tool['toolCategories']:
			cur.execute("INSERT INTO tool_category (tool_id, category_id) VALUES (%s, %s)", (toolID, category))
	print("successfully inserted tools");

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")

