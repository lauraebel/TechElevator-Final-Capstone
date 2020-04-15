import psycopg2
import json


path_to_file = input('Enter path to json file:')
with open(path_to_file, 'r') as data_file:
    data = json.load(data_file)

collection_array = []
for item in data:
    collection_array.append(json.dumps(item))



try:
    conn = psycopg2.connect(database="", user="", password="",host="127.0.0.1",port="5433")
    print ("opened  database successfully")
    cur = conn.cursor()

    for element in collection_array:
        cur.execute("INSERT INTO my_table (json_column_name) VALUES (%s)", (element,))
    print("successfully inserted records")    


except psycopg2.Error as e:
    raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")