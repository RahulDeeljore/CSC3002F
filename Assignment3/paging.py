# Rahul Deeljore
# RHLDEE001
# OS Assignment 1
# 08/04/19

import fileinput
from random import randint
import sys

def fifo(frames,pages):

	if frames >= len(pages):
		return len(pages)

	memory = []
	fifo = 0
		
	for page in pages:
		# if not in memory, pop the first one, append new page 
		# to memory
		if page not in memory:
			# remove a page only if memory is filled up
			if len(memory) == frames:
				del memory[0]
			memory.append(page)
			fifo = fifo + 1

	return fifo




def opt(frames,pages):

	if frames >= len(pages):
		return len(pages)

	memory = []
	opt = 0
	timeline_map = {}

	for i in range(0, len(pages)):
		timeline = timeline_map.get(pages[i], [])
		timeline.append(i)
		timeline_map[pages[i]] = timeline

	for page in pages:
		# if not in memory, remove the last one
		if page not in memory:
			# remove a page only if memory is filled up
			if len(memory) == frames:
				del memory[-1]
			opt = opt + 1

		# if in memory, remove the page from memory
		else:
			index = memory.index(page)
			del memory[index]

		# update timeline map: remove the first element in the 
		# list that map to page
		timeline = timeline_map.get(page)
		del timeline[0]
		timeline_map[page] = timeline


		# add the new page in a way such that the nearest page 
		# about to be used is always at the top of the stack.
		if timeline_map.get(page):
			index = 0
			time = timeline_map.get(page)[0]
			while index < len(memory):
				if memory:
					timeline = timeline_map.get(memory[index])
					if timeline and time > timeline[0]:
						index = index + 1
					else:
						break	
				else:
					break
		# if a page is not going to be used in the future, assume that
		# it is used after every pages
		else:
			index = len(memory)
		
		memory.insert(index, page)
		
 
	return opt




def lru(frames,pages):

	if frames >= len(pages):
		return len(pages)

	memory = []
	lru = 0

	for page in pages:
		# if not in memory, remove the last one
		if page not in memory:
			# remove a page only if memory is filled up
			if len(memory) == frames:
				del memory[-1]
			lru = lru + 1

		# if in memory, remove the page from memory
		else:
			index = memory.index(page)
			del memory[index]

		# always add new page at the beginning of memory stack
		memory.insert(0, page)
 
	return lru




def main():
    

    #generate random 32 digit
    pagesnum = randint(10000000000000000000000000000000,99999999999999999999999999999999) 
    pagesstr = str(pagesnum)
    frames = int(sys.argv[1]) # Command line argument is number of frames
    print"Random reference string of length 32 generated:"
    print(pagesstr)
    print
  
	
	  # slicing reference string into size 8,16,24 and 32
          # Then run each algorithm with each size
    for i in xrange (8,33,8):
	    print "Using reference string of size",i
	    print "Reference string:",pagesstr[0:i]
            pages = pagesstr[0:i]
	    print"Frames:",frames
	    faults = fifo(frames,pages)
	    print "FIFO: %d" % faults
	    faults = opt(frames,pages) 
	    print "OPT: %d" % faults         
	    faults = lru(frames,pages)  
	    print "LRU: %d" % faults
            print
	   

if __name__  == "__main__":
    if len(sys.argv) != 2:
        print 'Usage: python paging.py [number of pages]'
    else:
	main()
