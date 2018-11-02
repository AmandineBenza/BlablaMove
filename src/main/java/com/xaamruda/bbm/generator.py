import sys

def parser_CreateUser(argument):
    return "{'event':'create-user','data':{'mail':" + argument[1] + ",'name':" + argument[2] + ",'address':"+ argument[3] +",'phone':"+ argument[4] + ",'password':" + argument[5] + "}}"

def parser_CreateOffer(argument):
    return ""
def main(argument):
    header = "curl -H 'Accept: application/json' -H 'Content-type: application/json' -X POST -d "
    if(argument[0] == "create-user"):
        return header + parser_CreateUser(argument)
    elif(argument[0] == "create-offer"):
        return header + parser_CreateOffer(argument)
    else:
        return "Wrong event"

if __name__ == '__main__':
    print(main(sys.argv[1:]))