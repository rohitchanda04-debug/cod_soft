# University of Engineering & Management, Kolkata (UEM)
## B.Tech CSE — Student Management System v3.0
### ⚠️ AUTONOMOUS UNIVERSITY — NOT MAKAUT AFFILIATED

---

## 🏛️ About UEM Kolkata

| Field | Detail |
|-------|--------|
| **Full Name** | University of Engineering & Management, Kolkata |
| **Status** | **Autonomous University** (UGC Recognised, NAAC Accredited) |
| **Location** | Plot III-B/5, New Town, Action Area III, Kolkata – 700160 |
| **Website** | www.uem.edu.in |
| **Degrees awarded by** | UEM itself (NOT MAKAUT) |
| **Examination** | UEM's own internal examination board |
| **Syllabus** | UEM's own autonomous curriculum |

> UEM Kolkata is **not** affiliated to MAKAUT. It is an autonomous institution
> with its own grading system, roll number format, syllabus, and examination board.

---

## 🚀 Build & Run (VS Code)

```bash
# Requires Java 16+ (for records in editMarks)
javac -d out src/*.java
java  -cp out Main
```

---

## 📋 Marks Pattern (UEM Autonomous)

| Component | Marks | Description |
|-----------|-------|-------------|
| **Internal Assessment (IA)** | /40 | Assignments, Mid-Sems, Practicals, Attendance |
| **End-Semester Exam (ESE)**  | /60 | Annual university examination |
| **Total** | /100 | Combined score per subject |

### Pass Criteria (per subject)
- ✅ Total ≥ **40 / 100**
- ✅ External (ESE) ≥ **24 / 60** (minimum external pass mark)
- ✅ Attendance ≥ **75%** (eligibility for ESE)

---

## 🎓 UEM Grading Scale (10-Point Autonomous)

| Total (/100) | Grade | Description | Grade Points |
|--------------|-------|-------------|--------------|
| 90 – 100 | O  | Outstanding  | 10 |
| 80 – 89  | A+ | Excellent    | 9  |
| 70 – 79  | A  | Very Good    | 8  |
| 60 – 69  | B+ | Good         | 7  |
| 50 – 59  | B  | Above Avg    | 6  |
| 40 – 49  | C  | Pass         | 5  |
| 0  – 39  | F  | Fail         | 0  |

---

## 🆔 UEM Roll Number & Enrollment Format

| Field | Format | Example |
|-------|--------|---------|
| **UEM Roll No** | `BTECH/CSE/YYYY/NNN` | `BTECH/CSE/2022/001` |
| **Enrollment No** | `UEM/YYYY/CSE/NNNNN` | `UEM/2022/CSE/00001` |

---

## 📚 B.Tech CSE Autonomous Syllabus (UEM)

| Sem | Subjects |
|-----|----------|
| **1** | Mathematics I (Calculus & Linear Algebra), Physics, Chemistry, English & Communication Skills, Basic Electrical & Electronics Engg, Engineering Graphics & Design |
| **2** | Mathematics II (Probability & Statistics), Programming Fundamentals (C), Data Structures & Algorithms, Digital Logic Design, Environmental Science, Engineering/IT Workshop |
| **3** | Discrete Mathematics, OOP (Java), Computer Organisation & Architecture, Operating Systems, DBMS, Numerical Methods |
| **4** | DAA, Theory of Computation, Computer Networks, Software Engineering & Project Mgmt, Microprocessors & Embedded Systems, Elective I |
| **5** | Compiler Design, AI & Expert Systems, Machine Learning, Information & Cyber Security, Cloud Computing, Elective II |
| **6** | Deep Learning & Neural Networks, Big Data Analytics, IoT, Distributed Systems, Open Elective, Minor Project |
| **7** | Advanced CSE (Elective III), Elective IV, Internship/Industrial Training, Research Methodology, Seminar |
| **8** | Major Project, Entrepreneurship & Business Development, Professional Ethics & IPR, Comprehensive Viva |

---

## 📂 Project Structure

```
UEM_SMS_v2/
├── src/
│   ├── Student.java                   ← UEM autonomous data model (Int+Ext marks)
│   ├── StudentManagementSystem.java   ← CRUD + analytics + file I/O
│   ├── Validator.java                 ← UEM-specific input validation
│   └── Main.java                      ← Console UI (13 menu options)
├── out/                               ← Compiled classes
├── uem_cse_students.dat               ← Auto-created persistence file
└── README.md
```

---

## 💾 Data File Format (`uem_cse_students.dat`)

Pipe-delimited, 37 fields per line:
```
BTECH/CSE/2022/001|UEM/2022/CSE/00001|Aritra Bhattacharya|Male|12/08/2004|aritra.bhattacharya@uem.edu.in|9876543210|12 Lake Town Kolkata|4|A|2022-2026|7.85|B+|Day Scholar|Suresh Bhattacharya|Father|9876543211|Design & Analysis of Algorithms|35.0|52.0|Theory of Computation|32.0|48.0|....|82.5
```

---

## 📝 Sample Students for Testing

| UEM Roll No | Name | Sem | Section | Batch |
|-------------|------|-----|---------|-------|
| BTECH/CSE/2022/001 | Aritra Bhattacharya | 4 | A | 2022-2026 |
| BTECH/CSE/2022/002 | Priyanka Chatterjee | 4 | A | 2022-2026 |
| BTECH/CSE/2023/001 | Soumyadeep Mondal   | 3 | B | 2023-2027 |
| BTECH/CSE/2023/002 | Ankita Das          | 3 | B | 2023-2027 |
| BTECH/CSE/2021/001 | Rohan Mukherjee     | 6 | A | 2021-2025 |
