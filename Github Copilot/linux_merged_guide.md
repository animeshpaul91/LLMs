# Linux Commands for Software Engineers - Productivity Guide

## 🚀 Essential Linux Commands for Software Engineers

### 📁 File & Directory Management

#### Navigation & Listing

```bash
# Quick directory navigation
cd -                    # Go to previous directory
cd ~                    # Go to home directory
pushd /path/to/dir      # Push directory to stack
popd                    # Pop directory from stack
dirs -v                 # View directory stack

# Enhanced listing
ls -la                  # List all files with details
ls -lh                  # Human-readable file sizes
ls -lt                  # Sort by modification time
ls -la | grep "^d"      # List only directories
ls -la | grep "^-"      # List only files
tree                    # Tree view of directory structure
tree -L 2               # Limit depth to 2 levels
```

#### File Operations

```bash
# Copy with progress
rsync -av --progress source/ dest/
cp -rv source/ dest/    # Recursive copy with verbose

# Move with confirmation
mv -i file1 file2       # Interactive move
mv -n file1 file2       # No overwrite

# Find files quickly
find . -name "*.py" -type f    # Find Python files
find . -name "*.js" -mtime -7  # Find JS files modified in last 7 days
find . -size +100M             # Find files larger than 100MB
find . -empty                  # Find empty files/directories

# Advanced find with actions
find . -name "*.log" -exec rm {} \;    # Remove all log files
find . -name "*.tmp" -delete           # Delete temp files
```

### 🔍 Text Processing & Search

#### Powerful Search Tools

```bash
# grep variations
grep -r "pattern" .              # Recursive search
grep -n "pattern" file           # Show line numbers
grep -i "pattern" file           # Case insensitive
grep -v "pattern" file           # Invert match
grep -A 3 -B 3 "pattern" file    # Show 3 lines before/after
grep -l "pattern" *.py           # List files containing pattern

# ripgrep (faster alternative)
rg "pattern"                     # Search in current directory
rg -t py "pattern"              # Search only in Python files
rg -i "pattern"                 # Case insensitive
rg -n "pattern"                 # Show line numbers
rg -l "pattern"                 # List matching files only

# sed for text manipulation
sed 's/old/new/g' file          # Replace text
sed -i 's/old/new/g' file       # In-place replacement
sed -n '10,20p' file            # Print lines 10-20
sed '/pattern/d' file           # Delete lines matching pattern
```

#### File Content Analysis

```bash
# Quick file inspection
head -20 file                    # First 20 lines
tail -20 file                    # Last 20 lines
tail -f logfile                  # Follow log file in real-time
wc -l file                       # Count lines
wc -w file                       # Count words
wc -c file                       # Count characters

# Advanced text processing
awk '{print $1}' file           # Print first column
awk '/pattern/ {print $0}' file # Print lines matching pattern
awk 'NR==10 {print $0}' file    # Print 10th line
cut -d',' -f1,3 file            # Extract columns 1 and 3
sort file | uniq -c             # Count unique lines
```

### 🐳 Docker & Container Management

#### Docker Commands

```bash
# Container management
docker ps -a                     # List all containers
docker ps -q                     # List only container IDs
docker stop $(docker ps -q)      # Stop all running containers
docker rm $(docker ps -aq)       # Remove all containers
docker system prune -a           # Clean up everything

# Image management
docker images                    # List images
docker rmi $(docker images -q)   # Remove all images
docker build -t myapp .          # Build image
docker run -d -p 8080:80 myapp   # Run container in background

# Docker Compose
docker-compose up -d             # Start services in background
docker-compose down              # Stop and remove containers
docker-compose logs -f service   # Follow logs for specific service
docker-compose ps                # List running services
```

### 🌐 Network & Web Development

#### Network Tools

```bash
# Network diagnostics
netstat -tulpn                   # Show listening ports
ss -tulpn                        # Modern alternative to netstat
lsof -i :8080                    # What's using port 8080
curl -I http://localhost:8080    # Check HTTP headers
curl -X POST -d "data" url       # POST request
wget -O file.txt url             # Download file

# HTTP testing
httpie GET http://localhost:8080/api/users
httpie POST http://localhost:8080/api/users name=John
```

#### Process Management

```bash
# Process monitoring
ps aux | grep process            # Find process
ps -ef | grep python             # Find Python processes
kill -9 PID                      # Force kill process
pkill -f "process_name"          # Kill by process name
htop                             # Interactive process viewer
iotop                            # Monitor I/O usage
```

### 📊 System Monitoring

#### Resource Usage

```bash
# System resources
free -h                          # Memory usage (human readable)
df -h                            # Disk usage
du -sh *                         # Directory sizes
du -h --max-depth=1              # Directory sizes (1 level)
top                              # System monitor
htop                             # Enhanced top
nload                            # Network traffic monitor
```

#### Performance Analysis

```bash
# CPU and memory profiling
strace -p PID                    # Trace system calls
ltrace -p PID                    # Trace library calls
perf top                         # CPU profiling
valgrind --tool=memcheck ./app   # Memory leak detection
```

### 🛠 Development Tools

#### Git Productivity

```bash
# Git shortcuts
git status -s                    # Short status
git log --oneline -10            # Last 10 commits
git log --graph --oneline        # Visual commit history
git diff --cached                # Staged changes
git stash list                   # List stashes
git stash pop                    # Apply and remove stash
git branch -a                    # All branches
git checkout -b feature-branch   # Create and switch to branch
```

#### Package Management

```bash
# Ubuntu/Debian
apt update && apt upgrade        # Update system
apt search package               # Search packages
apt install package              # Install package
apt remove package               # Remove package

# Python
pip list                         # List installed packages
pip freeze > requirements.txt    # Export dependencies
pip install -r requirements.txt  # Install from requirements
```

### 🛠️ Custom Aliases & Functions

#### Add to ~/.bashrc or ~/.zshrc

```bash
# Navigation aliases
alias ll='ls -la'
alias la='ls -A'
alias l='ls -CF'
alias ..='cd ..'
alias ...='cd ../..'
alias ....='cd ../../..'

# Git aliases
alias gs='git status'
alias ga='git add'
alias gc='git commit'
alias gp='git push'
alias gl='git log --oneline'

# Docker aliases
alias dps='docker ps'
alias dimg='docker images'
alias dstop='docker stop $(docker ps -q)'
alias drm='docker rm $(docker ps -aq)'

# Development aliases
alias py='python3'
alias pip='pip3'
alias serve='python3 -m http.server 8000'
alias ports='netstat -tulpn'

# Custom functions
function mkcd() { mkdir -p "$1" && cd "$1"; }
function extract() { tar -xzf "$1"; }
function weather() { curl wttr.in/"$1"; }
```

### 📋 Log Analysis

#### Log Processing

```bash
# Real-time log monitoring
tail -f /var/log/nginx/access.log | grep "ERROR"
journalctl -f -u service-name    # Follow systemd service logs
docker logs -f container-name    # Follow Docker container logs

# Log analysis
grep "ERROR" logfile | wc -l     # Count errors
grep "ERROR" logfile | tail -20  # Last 20 errors
awk '/ERROR/ {print $1, $2}' logfile | sort | uniq -c  # Error count by time
```

### 🔒 Security & Permissions

#### File Permissions

```bash
# Permission management
chmod +x script.sh               # Make executable
chmod 755 directory              # Set directory permissions
chown user:group file            # Change ownership
sudo chown -R user:group dir/    # Recursive ownership change

# Security checks
ls -la | grep "^d" | awk '{print $3}' | sort | uniq -c  # Count files by owner
find . -perm -4000              # Find SUID files
find . -perm -2000              # Find SGID files
```

### 🚀 Performance Optimization

#### System Tuning

```bash
# File descriptor limits
ulimit -n 65536                  # Increase file descriptor limit
echo "* soft nofile 65536" >> /etc/security/limits.conf

# Kernel parameters
echo 'net.core.somaxconn = 65536' >> /etc/sysctl.conf
sysctl -p                        # Apply kernel parameters
```

### 🛠️ Additional Tools

#### Install These Tools

```bash
# Essential development tools
sudo apt install htop iotop nload tree ripgrep httpie jq

# Python development
pip install httpie jq-cli-wrapper

# Node.js tools
npm install -g nodemon concurrently
```

#### Useful One-liners

```bash
# Kill processes by name
pkill -f "node.*app.js"

# Find and replace in multiple files
find . -name "*.py" -exec sed -i 's/old/new/g' {} \;

# Monitor system resources
watch -n 1 'echo "CPU:" && top -bn1 | grep "Cpu(s)" && echo "Memory:" && free -h'

# Create backup with timestamp
tar -czf backup-$(date +%Y%m%d-%H%M%S).tar.gz directory/

# Find largest files
find . -type f -exec ls -lh {} \; | sort -k5 -hr | head -10
```

---

## 💡 Pro Tips

1. **Use aliases**: Create shortcuts for frequently used commands
2. **Master grep/awk/sed**: These are your Swiss Army knives for text processing
3. **Learn Docker**: Essential for microservices development
4. **Monitor resources**: Keep an eye on system performance
5. **Automate repetitive tasks**: Use scripts and functions
6. **Use version control**: Git is your friend
7. **Keep logs clean**: Regular log rotation and analysis
8. **Security first**: Regular permission audits and updates

Remember: The best command is the one you remember and use effectively. Start with the basics and gradually incorporate more advanced commands into your workflow!

---

# Unix & Linux Concepts and Applications

_Based on a session by Vishal Dawane_

---

## Table of Contents

- [Introduction to Unix](#introduction-to-unix)
- [Features of Unix](#features-of-unix)
- [Getting Around the System](#getting-around-the-system)
- [Unix File System Structure](#unix-file-system-structure)
- [Basic Commands](#basic-commands)
  - [File Operations](#file-operations)
  - [Directory Operations](#directory-operations)
- [Vi Editor](#vi-editor)
- [File Permissions](#file-permissions)
- [File Links](#file-links)
- [Redirection Operators](#redirection-operators)
- [Filters](#filters)
- [Shell Basics & Scripting](#shell-basics--scripting)
- [Variables & Quoting](#variables--quoting)
- [Scripting Constructs](#scripting-constructs)
- [Command Line Arguments](#command-line-arguments)
- [Functions](#functions)
- [Arrays](#arrays)
- [sed](#sed)
- [awk](#awk)
- [Unix Help](#unix-help)
- [Shell & User Profile](#shell--user-profile)
- [Job Scheduling](#job-scheduling)
- [Archiving and Compression](#archiving-and-compression)

---

## Introduction to Unix

- **UNIX** stands for UNIplexed Information and Computing System.
- Developed by Ken Thompson and Dennis Ritchie at Bell Labs.
- **Vendors:** Sun (Solaris), IBM (AIX), HP (HP-UX), Apple (Macintosh), Linux (open source by Linus Torvalds).
- **Linux Distributions:** RedHat (RHEL, Fedora), Suse, Mandrake, Caldera.

---

## Features of Unix

1. **Portable OS**
2. **Multi-user**
3. **Multi-tasking**
4. **Multi-processing**
5. **Secured** (permissions: r w x)
6. **Robust**
7. **Distributed**

---

## Getting Around the System

- **Connect to a Unix server:** Use SSH or terminal access.
- **Logout:** Use `exit` or `logout` command.

---

## Unix File System Structure

```
/         (root)
|-- bin      # Essential binaries (e.g., date, ls)
|-- usr      # User system resources
|   |-- bin      # More binaries (e.g., 2500+)
|   |-- sbin     # System binaries
|-- sbin     # System binaries (e.g., shutdown)
|-- etc      # Configuration files
|-- dev      # Device files
|-- home     # User home directories
|-- lib      # Shared libraries (.so)
|-- tmp      # Temporary files
|-- var      # Variable data (logs, spools)
|-- mnt      # Mount points for devices
|-- opt      # Third-party software
|-- proc     # Process info (virtual filesystem)
```

- **Hidden files:** Any file starting with a dot (.) is hidden. Use `ls -a` to view all files.

---

## Basic Commands

### File Operations

| Operation | Command Example          | Description               |
| --------- | ------------------------ | ------------------------- |
| Create    | `cat > filename`         | Create a file             |
| View      | `cat filename`           | View file content         |
| Copy      | `cp abc.txt /tmp`        | Copy file to directory    |
|           | `cp abc.txt pqr.txt`     | Copy file to another file |
| Rename    | `mv prince.txt king.txt` | Rename file               |
| Move      | `mv king.txt /tmp`       | Move file to directory    |
| Remove    | `rm abc.txt`             | Delete file               |

### Directory Operations

| Operation | Command Example               | Description                  |
| --------- | ----------------------------- | ---------------------------- |
| Create    | `mkdir dirname`               | Create directory             |
| View      | `ls directoryName`            | List contents of directory   |
|           | `cd directory` + `ls`         | Change and list              |
| Copy      | `cp -r directory destination` | Copy directory recursively   |
| Rename    | `mv oldname newname`          | Rename directory             |
| Move      | `mv directory destination`    | Move directory               |
| Remove    | `rm -r directoryname`         | Delete directory recursively |

#### Paths

- **Relative path:** `office/mail/inbox/letter.doc`
- **Absolute path:** `/etc`

---

## Vi Editor

- **vi**: Visual editor (default on Unix systems)
- **vim**: Vi Improved

### Modes

- **Command mode** (default)
- **Insert mode** (`i` to enter)
- **Last line mode** (`:` to enter)

### Useful Commands

| Command         | Description            |
| --------------- | ---------------------- |
| `i`             | Insert mode            |
| `:`             | Last line mode         |
| `w`             | Move wordwise ahead    |
| `b`             | Move wordwise backward |
| `e`             | Move to end of word    |
| `^`             | Start of line          |
| `$`             | End of line            |
| `dd`            | Delete line            |
| `u`             | Undo                   |
| `dw`            | Delete word            |
| `:w`            | Save                   |
| `:q`            | Quit                   |
| `:wq`           | Save and quit          |
| `:q!`           | Quit without saving    |
| `:set number`   | Show line numbers      |
| `:set nonumber` | Hide line numbers      |

### Steps to Create/Edit File

1. `vi filename`
2. Press `i` to enter insert mode
3. Type content
4. Press `Esc` to return to command mode
5. Use `:wq` to save and exit

---

## File Permissions

- **Types:**

  - Read (4)
  - Write (2)
  - Execute (1)

- **Users:**

  - User (u)
  - Group (g)
  - Other (o)

- **File Types:**
  - `-` : File
  - `d` : Directory
  - `l` : Soft link
  - `s` : Socket
  - `c` : Character device
  - `b` : Block device

### Viewing Permissions

- `ls -l office` : Shows content of office
- `ls -ld office` : Shows permission details

### Changing Permissions

- `chmod u+r abc` : User read
- `chmod g+r abc` : Group read
- `chmod o+r abc` : Other read
- `chmod u+rwx abc` : User all
- `chmod ug+rwx abc` : User, group all
- `chmod ugo+rwx abc` : All
- `chmod ug-rw abc` : Remove read/write from user/group
- `chmod u+r,g-w,o+x abc` : Add read to user, remove write from group, add execute to others

### Default Permissions

- Controlled by `umask`
- Typical base: files (666), directories (777)

---

## File Links

### Soft Link (Symbolic Link)

- `ln -s TARGET softlinkname`
- Acts like a shortcut
- Can link across partitions
- Broken if target is moved/removed

### Hard Link

- Shares file across directories (same partition)
- No duplicate copies
- File deleted only when all links are removed
- Cannot create hard link for directories

---

## Redirection Operators

| Operator | Description        | Example          |
| -------- | ------------------ | ---------------- | ----- | ---- | ----- |
| `>`      | Output redirection | `cat > abc.txt`  |
| `<`      | Input redirection  | `cat < abc.txt`  |
| `>>`     | Append             | `cat >> abc.txt` |
| `        | `                  | Pipe             | `cmd1 | cmd2 | cmd3` |

---

## Filters

### 1. `wc` - Word/line/char count

- `wc -l file` : Line count
- `wc -w file` : Word count
- `wc -c file` : Character count

### 2. `head` - Top lines

- `head -n 5 file` : First 5 lines

### 3. `tail` - Bottom lines

- `tail -n 5 file` : Last 5 lines

### 4. `cut` - Vertical cut

- `cut -f 1 file` : First field (tab delimiter)
- `cut -d "|" -f 1 file` : First field (| delimiter)

### 5. `grep` - Pattern search

- `grep PATTERN fileList`
- Supports regex, color highlighting, recursive search, etc.
- `egrep` for extended regex
- `fgrep` for fixed string search

### 6. `tr` - Translate/replace characters

- `tr "a" "b"`
- `tr -s " "` (squeeze spaces)

### 7. `sort` - Sort lines

- `sort -t "|" -k1 file` : Sort by first field
- `sort -u` : Unique sort

### 8. `uniq` - Remove duplicates

- Use after `sort` for best results

---

## Shell Basics & Scripting

- **Shell:** Interface between user and kernel
- Popular shells: Bourne (`sh`), Korn (`ksh`), C Shell (`csh`), Bash (`bash`)

### Scripting vs Programming

| Scripting      | Programming    |
| -------------- | -------------- |
| Interpreted    | Compiled       |
| Controls logic | Develops logic |

### Example Script

```sh
# first.sh
cal
date
whoami
```

- Make executable: `chmod u+x first.sh`
- Run: `./first.sh`

---

## Variables & Quoting

- Assign: `VARNAME=VALUE`
- Access: `$VARNAME`
- Arithmetic: `expr $A + $B`, `$((A+B))`
- Command substitution: `` echo "today is `date`" `` or `echo "today is $(date)"`

### Quoting

- Double quotes (`"`): Expands variables
- Single quotes (`'`): Literal
- Backticks (`` ` ``): Command substitution

---

## Scripting Constructs

### Sequence

- Execute lines sequentially

### Selection

- `if [ condition ]; then ... else ... fi`
- `case` statement

### Iteration

- `while`, `until`, `for` loops

### Exit Status

- Every command returns exit status (0: success)

---

## Command Line Arguments

- `$#` : Number of arguments
- `$*` : All arguments
- `$1`, `$2`, ... : Positional arguments

---

## Functions

- Define:

```sh
myfunc() {
  # code
  return value
}
```

- Source from another file: `. ./funLib.sh`
- Current process id: `$$`

---

## Arrays

- Define: `ary=(10 20 30 40 50)`
- Access:
  - All: `${ary[*]}`
  - Length: `${#ary[*]}`
  - Index: `${ary[3]}`

---

## sed (Stream Editor)

- Non-interactive file editing
- Actions: print (`p`), append (`a`), insert (`i`), substitute (`s`), delete (`d`), search (`/pattern/`)

### Examples

- Print 3rd line: `sed -n '3p' file`
- Print lines 4-7: `sed -n '4,7p' file`
- Search pattern: `sed -n '/sharma/p' file`
- Append after line: `sed '12a data' file`
- Insert before line: `sed '12i data' file`
- Substitute: `sed 's/old/new/' file`
- Delete last line: `sed '$d' file`

---

## awk

- For report generation and text processing
- Default delimiter: whitespace

### Examples

- Print first column: `awk '{print $1}' file`
- Print formatted: `awk '{printf("%d|%s|%s|%9.2f\n",$1,$2,$3,$4)}' file`
- Use custom delimiter: `awk -F"|" '{print $1}' file`
- Conditional: `awk '{if ($3=="java") print $0}' file`

---

## Unix Help

- `man ls` : Manual page
- `ls --help` : Help option
- `info ls` : Info documentation

---

## Shell & User Profile

- **.bashrc:** User shell configuration (aliases, functions)
- **.bash_profile:** Runs once at login
- `export VAR`: Make variable global
- `set`: View environment
- `PS1`: Customize prompt (`[\u@\h \W]\$`)

---

## Job Scheduling

- `at`: One-time job scheduling
- `crontab`: Recurring jobs

---
