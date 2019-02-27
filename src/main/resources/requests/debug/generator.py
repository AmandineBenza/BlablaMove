import sys

def parser_CreateUser(argument):
    return "\"{'event':'create-user','data':{'mail':" + argument[1] + ",'name':" + argument[2] + ",'address':"+ argument[3] +",'phone':"+ argument[4] + ",'password':" + argument[5] + "}}\" 'localhost:8080/BBM/USERS/'"

def parser_CreateOffer(argument):
    return "\"{'event':'create-offer','data':{"+ " " + "}}\"  'localhost:8080/BBM/USERS/'"

def parser_IdentifyUser(argument):
    return "\"{'event':'identify-user','data':{"+ " " + "}}\"  'localhost:8080/BBM/USERS/'"

def parser_ConsultUser(argument):
    return "\"{'event':'consult-users','data':{'mail':" + argument[1] + "}}\" 'localhost:8080/BBM/USERS/' "

def parser_ConsultOffer(argument):
    return  "\"{'event':'consult-offer','data':{"+ " " + "}}\"  'localhost:8080/BBM/USERS/'"

def main(argument):
    header = "curl -H 'Accept: application/json' -H 'Content-type: application/json' -X POST -d "
    if(argument[0] == "create-user"):
        return header + parser_CreateUser(argument)
    elif(argument[0] == "create-offer"):
        return header + parser_CreateOffer(argument)
    elif(argument[0] == "consult-offer"):
        return header + parser_ConsultOffer(argument)
    elif(argument[0] == "identify-user"):
        return header + parser_IdentifyUser(argument)
    elif(argument[0] == "consult-user"):
        return header + parser_ConsultUser(argument)
    else:
        return "Wrong event"

if __name__ == '__main__':
    print(main(sys.argv[1:]))
