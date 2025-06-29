#!/usr/bin/env python3
"""CLI to-do list application with persistent storage."""

import sys
import json

TASKS_FILE = "tasks.json"

def load_tasks():
    try:
        with open(TASKS_FILE) as f:
            return json.load(f)
    except (FileNotFoundError, json.JSONDecodeError):
        return []

def save_tasks(tasks):
    with open(TASKS_FILE, 'w') as f:
        json.dump(tasks, f)

def main():
    tasks = load_tasks()
    
    if len(sys.argv) < 2:
        print("Usage: python starter_app.py {add \"<task>\"|list|delete <num>}")
        return
    
    cmd = sys.argv[1].lower()
    
    if cmd == "add" and len(sys.argv) > 2:
        tasks.append(sys.argv[2])
        save_tasks(tasks)
        print(f"Added: '{sys.argv[2]}'")
    elif cmd == "list":
        print("No tasks found." if not tasks else 
              "\n".join(f"{i}. {task}" for i, task in enumerate(tasks, 1)))
    elif cmd == "delete" and len(sys.argv) > 2:
        try:
            idx = int(sys.argv[2]) - 1
            if 0 <= idx < len(tasks):
                print(f"Deleted: '{tasks.pop(idx)}'")
                save_tasks(tasks)
            else:
                print(f"Task {sys.argv[2]} not found.")
        except ValueError:
            print("Invalid task number.")
    else:
        print("Invalid command. Use: add, list, or delete")

if __name__ == "__main__":
    main()
    