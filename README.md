"# Website-Mining" 

The aim of this coursework is to implement a multi-threaded application to analyse the word frequency on
a given website.

MinerManager Thread

rootURL: String // root/base URL where the mining starts
Q:Queue //a FIFO queue for storing the URLs to be visited
visited:List //contains a list of URLs already visited
results:Map<String, Integer>
// used for storing occurrences (count) of each keyword

Pseudocode for BFS search:

Main thread(MineManager)   
enqueue rootURL to Q.    
while Q is not empty then    
start a new WebMiner thread t and invoke t.mine(URL)

WebMiner threads:  
procedure mine(URL)  
dequeue a URL from Q  
add URL to visited  
count words, update results  
for each hyperlink on the page  
enqueue hyperlink onto Q