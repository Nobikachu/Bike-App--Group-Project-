#!/usr/bin/python

import googlemaps
import csv

client = googlemaps.Client("AIzaSyDNYtEje8tBXYH9R9rYH0XV878AyIAlA0w")
#coordinates = client.geocode(location).get('geometry').get('location')
outfile = open('coords.txt', 'w')
i = 0
bounds={'southwest': (40.681239,  -74.040300), 'northeast':(40.879077, -73.902971)}

with open('Lower_Manhattan_Retailers.csv', newline='') as csvfile:
    reader = csv.reader(csvfile, delimiter=',', quotechar='|')
    for row in reader:
        print(i)
        i += 1
        coordinates = client.geocode(row[1] + ', ' + 'Manhattan' + ',' + row[3] + ', ' + row[4], bounds=bounds)[0].get('geometry').get('location')
        outfile.write(','.join(row) + ',' + (str(coordinates.get('lat')) + ',' + str(coordinates.get('lng'))) + '\n')
        
outfile.close()
