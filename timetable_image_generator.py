#!/usr/bin/env python3
"""
Timetable Image Generator
Receives JSON timetable data and generates a visual timetable image.
Designed for Mon-Fri, 8-20h schedule format with 2-hour time slots.
Supports Romanian day names and activity types.
Layout: Days (vertical) × Hours (horizontal)
"""

import json
import sys
import os
from datetime import datetime
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.patches import Rectangle
import numpy as np

# Constants
DAYS = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
ROMANIAN_DAYS = ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri']
DAY_MAPPING = {
    'Luni': 'Monday',
    'Marți': 'Tuesday', 
    'Miercuri': 'Wednesday',
    'Joi': 'Thursday',
    'Vineri': 'Friday',
    # English variants (for compatibility)
    'Monday': 'Monday',
    'Tuesday': 'Tuesday',
    'Wednesday': 'Wednesday', 
    'Thursday': 'Thursday',
    'Friday': 'Friday'
}

# Time slots: 8-10, 10-12, 12-14, 14-16, 16-18, 18-20 (6 slots of 2 hours each)
TIME_SLOTS = ['8:00-10:00', '10:00-12:00', '12:00-14:00', '14:00-16:00', '16:00-18:00', '18:00-20:00']
HOUR_STARTS = [8, 10, 12, 14, 16, 18]  # Starting hours for each slot

# Simplified colors for Romanian activity types
COLORS = {
    'Curs': '#4CAF50',        # Green - Lecture
    'Seminar': '#2196F3',     # Blue - Seminar  
    'Laborator': '#FF9800',   # Orange - Laboratory
    'default': '#9E9E9E'      # Grey - Default
}

# Activity type abbreviations
ACTIVITY_ABBREVIATIONS = {
    'Curs': 'CURS',
    'Seminar': 'SEM', 
    'Laborator': 'LAB',
    'default': 'ACT'
}

def parse_json_input(json_str):
    """Parse JSON input and return structured schedule data"""
    try:
        data = json.loads(json_str)
        return data
    except json.JSONDecodeError as e:
        print(f"Error parsing JSON: {e}")
        return None

def create_timetable_grid():
    """Create the basic timetable grid with days vertical and hours horizontal"""
    fig, ax = plt.subplots(figsize=(16, 10))
    
    # Set up the grid - REVERSED: Hours (6 slots) horizontal, Days (5) vertical
    ax.set_xlim(0, len(TIME_SLOTS))   # X-axis: 6 time slots
    ax.set_ylim(0, len(DAYS))         # Y-axis: 5 days
    
    # Add time slot labels (horizontal) 
    ax.set_xticks(np.arange(len(TIME_SLOTS)) + 0.5)
    ax.set_xticklabels(TIME_SLOTS, fontsize=11, fontweight='bold', rotation=0)
    
    # Add day labels (vertical - using Romanian day names for display)
    ax.set_yticks(np.arange(len(DAYS)) + 0.5)
    ax.set_yticklabels(ROMANIAN_DAYS, fontsize=12, fontweight='bold')
    
    # Invert y-axis so Monday is at the top
    ax.invert_yaxis()
    
    # Add grid lines
    ax.set_xticks(np.arange(len(TIME_SLOTS) + 1), minor=True)
    ax.set_yticks(np.arange(len(DAYS) + 1), minor=True)
    ax.grid(which='minor', color='lightgray', linestyle='-', linewidth=0.5)
    
    # Labels
    ax.set_xlabel('Interval orar (2 ore)', fontsize=12, fontweight='bold')
    ax.set_ylabel('Ziua săptămânii', fontsize=12, fontweight='bold')
    
    return fig, ax

def add_schedule_entries(ax, schedule_data):
    """Add schedule entries to the timetable grid"""
    entries = schedule_data.get('entries', [])
    
    print(f"Processing {len(entries)} entries...")
    
    for entry in entries:
        day = entry.get('day', '')
        time_slot = entry.get('time', '')
        course = entry.get('course', '')
        activity_type = entry.get('type', 'default')
        teacher = entry.get('teacher', '')
        room = entry.get('room', '')
        
        print(f"Processing entry: Day={day}, Time={time_slot}, Course={course}, Type={activity_type}")
        
        # Parse time slot (e.g., "08:00 - 10:00")
        if ' - ' in time_slot:
            start_time, end_time = time_slot.split(' - ')
            start_hour = int(start_time.split(':')[0])
            end_hour = int(end_time.split(':')[0])
        else:
            # Single hour slot
            start_hour = int(time_slot.split(':')[0])
            end_hour = start_hour + 2  # Default 2-hour duration
            
        # Map Romanian day to English and get day index
        mapped_day = DAY_MAPPING.get(day)
        if mapped_day and mapped_day in DAYS:
            day_idx = DAYS.index(mapped_day)
            print(f"  Mapped day '{day}' to index {day_idx}")
        else:
            print(f"  Skipping invalid day: {day}")
            continue  # Skip invalid days
            
        # Find the correct time slot index based on start hour
        time_idx = -1
        for i, slot_start in enumerate(HOUR_STARTS):
            if start_hour == slot_start:
                time_idx = i
                break
                
        if time_idx == -1:
            print(f"  Skipping invalid time slot: {start_hour}:00-{end_hour}:00")
            continue
            
        print(f"  Time slot: {start_hour}-{end_hour}, time_idx={time_idx}, day_idx={day_idx}")
        
        # Choose color based on activity type
        color = COLORS.get(activity_type, COLORS['default'])
        print(f"  Using color {color} for type {activity_type}")
        
        # Create rectangle for the time slot - COORDINATES REVERSED
        # X = time slot index, Y = day index
        rect = Rectangle((time_idx, day_idx), 1, 1, 
                        facecolor=color, alpha=0.7, edgecolor='black', linewidth=1)
        ax.add_patch(rect)
        
        # Create formatted text label
        activity_abbrev = ACTIVITY_ABBREVIATIONS.get(activity_type, 'ACT')
        
        # Format course info - assume course is the ID for now, but make it uppercase
        course_text = course.upper() if course else ''
        
        # Format teacher - if it starts with 'T' it's probably an ID, otherwise it's a name
        if teacher.startswith('T') and teacher[1:].isdigit():
            # It's an ID like T001, T003 - we should show this for now
            teacher_text = teacher
        else:
            # It's a full name - show as is
            teacher_text = teacher
            
        # Format room
        room_text = room if room else ''
        
        # Build the label with multiple lines
        label_lines = []
        if activity_abbrev:
            label_lines.append(f"[{activity_abbrev}]")
        if course_text:
            label_lines.append(course_text)
        if teacher_text:
            label_lines.append(teacher_text)
        if room_text:
            label_lines.append(room_text)
            
        label_text = '\n'.join(label_lines)
        
        # Font size for 2-hour slots
        font_size = 9
        
        # Add text centered in the rectangle - COORDINATES REVERSED
        ax.text(time_idx + 0.5, day_idx + 0.5, label_text,
               ha='center', va='center', fontsize=font_size, weight='bold',
               bbox=dict(boxstyle="round,pad=0.15", facecolor='white', alpha=0.95, edgecolor='darkgray'),
               linespacing=1.2)
        
        print(f"  Added rectangle at ({time_idx}, {day_idx}) with size (1, 1)")
        print(f"  Text: {repr(label_text)}")

def save_timetable_image(schedule_data, output_path):
    """Generate and save timetable image"""
    try:
        # Create the grid
        fig, ax = create_timetable_grid()
        
        # Add title
        title = schedule_data.get('name', 'Orar')
        entity_type = schedule_data.get('type', '')
        if entity_type:
            if entity_type == 'STUDENT_GROUP':
                title = f"Grupa {title}"
            elif entity_type == 'TEACHER':
                title = f"Profesor {title}"
            elif entity_type == 'ROOM':
                title = f"Sala {title}"
        
        ax.set_title(f"Orar Universitar - {title}", fontsize=16, fontweight='bold', pad=20)
        
        # Add schedule entries
        add_schedule_entries(ax, schedule_data)
        
        # Add legend with simplified color scheme
        legend_elements = []
        for activity_type, color in COLORS.items():
            if activity_type != 'default':
                legend_elements.append(patches.Patch(color=color, label=activity_type))
        
        if legend_elements:
            ax.legend(handles=legend_elements, loc='upper left', bbox_to_anchor=(1.02, 1))
        
        # Adjust layout and save
        plt.tight_layout()
        plt.savefig(output_path, dpi=300, bbox_inches='tight')
        plt.close()
        
        return True
        
    except Exception as e:
        print(f"Error generating timetable image: {e}")
        import traceback
        traceback.print_exc()
        return False

def main():
    """Main function to process command line arguments and generate image"""
    if len(sys.argv) != 3:
        print("Usage: python3 timetable_image_generator.py <json_file> <output_image>")
        print("Example: python3 timetable_image_generator.py schedule.json timetable.png")
        sys.exit(1)
    
    json_file = sys.argv[1]
    output_file = sys.argv[2]
    
    # Check if JSON file exists
    if not os.path.exists(json_file):
        print(f"Error: JSON file '{json_file}' not found")
        sys.exit(1)
    
    # Read JSON data
    try:
        with open(json_file, 'r') as f:
            json_content = f.read()
    except Exception as e:
        print(f"Error reading JSON file: {e}")
        sys.exit(1)
    
    # Parse JSON
    schedule_data = parse_json_input(json_content)
    if not schedule_data:
        sys.exit(1)
    
    # Generate image
    success = save_timetable_image(schedule_data, output_file)
    
    if success:
        print(f"Timetable image generated successfully: {output_file}")
        print(f"Full path: {os.path.abspath(output_file)}")
    else:
        print("Failed to generate timetable image")
        sys.exit(1)

if __name__ == "__main__":
    main() 