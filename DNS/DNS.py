import socket
try:
    print('\nDNS in Python........\n')
    ch = int(input(" 1. URL to IP \n 2. IP to URL \n Enter Your Option: "))

    if(ch==1):
        var = input('Enter URL ')
        var2 = socket.gethostbyname(var)
        print(f"IP address for URL {var}: {var2}")

    if(ch==2):
        var = input('Enter IP ')
        var2 = socket.gethostbyaddr(var)
        print(f"URL for IP address {var}: {var2[0]}")

except Exception:
    print('An Error has occured')