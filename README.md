# JSON-Database
My JSON database program

Overview:
Through this project, I was able to correctly create a database of JSON objects, by saving them within json files. The client would request to the server to either set, get, or delete JSON objects that are stored on the server-side. 

Project Details:

This project helped me grasp the concept of using both JSON, as well as parralization and working with multiple clients at the same time. This included building both the client side and the server side. Furthermore, I was able to build a server that was able to take multiple requests (a multithreaded server) from different clients at the same time; with the help of the Executor API, I was able to take in multiple clients at once. 

The JSON database project also helped me understand how to correctly utilize the JCommander framework in order to parse command-line parameters. This was done on the client-side. After the command line arguments were parsed and sent to the server, I utilized the Gson API in order to correctly serialize/deserialize strings into and from JSON objects. The server would save the strings that it received as JSON objects within a Json in order for the client to retrieve them at a later time. Furthermore, files from the client side were also read in order to either set, delete, or get files from the server; these files contained information regarding which request to send to the server. This also included sending multiple JSON objects at once. In order to save these array of JSON objects, I also had to use JSON element, JSON array and other methods found within the Gson API. 

In order to handle multiple client requests, the ReentrantReadWriteLock class was used so as to make sure only one client could write to a file at a time in order to avoid conflicts between writing and saving to json files. 

How I'd Like to improve this Project:

I think that I would like to improve this project by taking advantage of the Command pattern. The Command Pattern might help me get rid of a lot of boilerplate code, as well as make it easier for others to read. As I am still learning about design patterns, I would love to implement the Command Pattern so as to make the set, get, and delete options more concise and easier to read. I would also like to be able to save files that are retrieved from the server to a folder on the client side. 
