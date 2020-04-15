import psycopg2
import json
import datetime

path_to_file = './sampleusers.json'
with open(path_to_file, 'r') as user_file:
    users = json.load(user_file)

path_to_file = './sampleloans.json'
with open(path_to_file, 'r') as loan_file:
    loans = json.load(loan_file)

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	
	for user in users:
		cur.execute("INSERT INTO users (first_name, last_name, email, username, password, salt, role) VALUES (%s, %s, %s, %s, %s, %s, %s)", (user['firstName'], user['lastName'], user['email'], user['username'], user['password'], user['salt'], user['role'],))
	print("successfully inserted users");

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")

try:
	conn = psycopg2.connect(database="tool_library", user="capstone_owner", password="capstone_owner1",host="127.0.0.1",port="5432")
	print ("opened  database successfully")
	cur = conn.cursor()

	for loan in loans:
		if 'returnyear' in loan:
			cur.execute("INSERT INTO loans (user_id, tool_id, loaned_on, due_on, returned_on) VALUES (%s, %s, %s, %s, %s)", (loan['userId'], loan['toolId'], datetime.date(loan['loanedyear'], loan['loanedmonth'], loan['loanedday']), datetime.date(loan['dueyear'], loan['duemonth'], loan['dueday']), datetime.date(loan['returnyear'], loan['returnmonth'], loan['returnday']),))
		else:
			cur.execute("INSERT INTO loans (user_id, tool_id, loaned_on, due_on) VALUES (%s, %s, %s, %s)", (loan['userId'], loan['toolId'], datetime.date(loan['loanedyear'], loan['loanedmonth'], loan['loanedday']), datetime.date(loan['dueyear'], loan['duemonth'], loan['dueday']),))
	print("successfully inserted loans");

except psycopg2.Error as e:
	raise

finally:
    conn.commit()
    conn.close()
    print("connection is closed")
