import csv
import json
from collections import defaultdict

# File path to your CSV
csv_file = r'C:\api\Shlokas - Sheet1.csv'

# Initialize the structure for the final JSON output
output = {"Chapters": defaultdict(lambda: {"verses": defaultdict(dict)})}

try:
    # Open the CSV file
    with open(csv_file, mode='r', encoding='utf-8') as file:
        reader = csv.DictReader(file)
        
        # Print CSV headers for debugging
        print("CSV Headers:", reader.fieldnames)
        
        # Process each row
        for row in reader:
            chapter_key = f"Chapter{int(row['chapter Number']):02d}"  # Match with lowercase 'chapter Number'
            verse_key = f"verse{row['Verse Number']}"
            
            # Map CSV data to the JSON structure
            output["Chapters"][chapter_key]["chapterName"] = row["Chapter Name"]
            output["Chapters"][chapter_key]["chapterNumber"] = int(row["chapter Number"])
            output["Chapters"][chapter_key]["imageUrl"] = row["Image url"]
            output["Chapters"][chapter_key]["totalVerses"] = int(row["Total Verses"])
            output["Chapters"][chapter_key]["verses"][verse_key] = {
                "english": row["English Translation"],
                "explanation": row["Explanation"],
                "sanskrit": row["Sanskrit"],
                "youtubeLink": row["youtubeLink"]
            }
    
    # Convert the defaultdict to a regular dict for JSON serialization
    output["Chapters"] = dict(output["Chapters"])
    
    # Write the output to a JSON file
    with open('output.json', 'w', encoding='utf-8') as json_file:
        json.dump(output, json_file, ensure_ascii=False, indent=4)
    
    print("JSON data has been saved to output.json")

# Handle errors
except FileNotFoundError as fnf_error:
    print(f"Error: {fnf_error}")

except KeyError as key_error:
    print(f"Error: {key_error}. Check if the CSV file has the correct headers: {reader.fieldnames}")

except Exception as e:
    print(f"An unexpected error occurred: {e}")
