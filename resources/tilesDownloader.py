#!/usr/bin/python

from sys import argv
import os
import math
import urllib2
import random
import os.path

def deg2num(lat_deg, lon_deg, zoom):
    lat_rad = math.radians(lat_deg)
    n = 2.0 ** zoom
    xtile = int((lon_deg + 180.0) / 360.0 * n)
    ytile = int((1.0 - math.log(math.tan(lat_rad) + (1 / math.cos(lat_rad))) / math.pi) / 2.0 * n)
    return (xtile, ytile)

def download_url(zoom, xtile, ytile):
    # Switch between otile1 - otile4
    subdomain = random.randint(1, 4)
    
    url = "https://cartodb-basemaps-a.global.ssl.fastly.net/light_all/%d/%d/%d.png" % (zoom, xtile, ytile)
    dir_path = "mapTiles/%d/%d/" % (zoom, xtile)
    download_path = "mapTiles/%d/%d/%d.png" % (zoom, xtile, ytile)
    
    if not os.path.exists(dir_path):
        os.makedirs(dir_path)
    
    if(not os.path.isfile(download_path)):
        print "downloading %r" % url
        source = urllib2.urlopen(url)
        content = source.read()
        source.close()
        destination = open(download_path,'wb')
        destination.write(content)
        destination.close()
    else: print "skipped %r" % url

def usage():
    print "Usage: "
    print "tilesDownloader <lat> <lon>"

def main(argv):
    try:
        script, lat, lon = argv
    except:
        usage()
        exit(2)

    maxzoom = 7 # redefine me if need so

    # from 0 to 6 download all
    for zoom in range(0,maxzoom+1,1):
        for x in range(0,2**zoom,1):
            for y in range(0,2**zoom,1):
                download_url(zoom, x, y)    
main(argv)    