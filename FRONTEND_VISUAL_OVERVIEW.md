# AlphaWork Frontend - Visual Design Overview

## 🎨 Complete UI Layout Map

```
┌──────────────────────────────────────────────────────────────┐
│ AlphaWork    [Search Issues, Projects...]    🔔  👤        │ ← NAVBAR (64px)
│ Logo        (Center Search Bar)             🎯  Menu       │ Gradient: #667eea → #764ba2
├────────┬────────────────────────────────────────────────────┤
│        │                                                      │
│ SIDEBAR│                    MAIN CONTENT AREA                │
│        │                                                      │
│ ├─ 🏠  │                    (Responsive Grid Layout)         │
│ │ Dash │                                                      │
│ │      │   ┌──────────┐  ┌──────────┐  ┌──────────┐        │
│ ├─ 📁  │   │ 12 Projs │  │ 48 Issues│  │ 7 Doing  │        │
│ │ Proj │   │ (Stats)  │  │ (Stats)  │  │ (Stats)  │        │
│ │      │   └──────────┘  └──────────┘  └──────────┘        │
│ ├─ 📊  │                                                      │
│ │Board │   Project Grid (3 columns)                         │
│ │      │  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ ├─ ✓   │  │ Project Name │ │ Project Name │ │ Project Name │
│ │Issues│  │ Kanban       │ │ Kanban       │ │ Scrum        │
│ │      │  │ ▓▓▓ 60%      │ │ ▓▓▓ 85%      │ │ ▓▓ 45%       │
│ ├─ ─── │  │ [View]       │ │ [View]       │ │ [View]       │
│ │      │  └──────────────┘ └──────────────┘ └──────────────┘
│ │ 🏢   │
│ │ Org  │   Recent Issues
│ │      │  ┌─────────────────────────────────────────────────┐
│ │      │  │ PRJ-1 │ Login bug      │ IN REVIEW │ 👤 Updated│
│ │      │  ├─────────────────────────────────────────────────┤
│ │      │  │ PRJ-2 │ Add feature    │ DOING     │ 👤 In 2h  │
│ │      │  ├─────────────────────────────────────────────────┤
│ │      │  │ PRJ-3 │ Database fix   │ BLOCKED   │ 👤 Urgent │
│ │      │  └─────────────────────────────────────────────────┘
│ │      │
│ └─ ➕  │
│   New  │
│   Proj │
│        │
└────────┴────────────────────────────────────────────────────┘
```

---

## 📱 Responsive Breakpoints

### Desktop (1200px+)
```
Full Sidebar (256px)    |    3-Column Grid
Visible Navigation      |    All Features
Full Search Bar         |    Desktop Menu
```

### Tablet (768px - 1199px)
```
Collapsible Sidebar     |    2-Column Grid
Hamburger Menu          |    Optimized Spacing
Abbreviated Labels      |    Touch Buttons
```

### Mobile (<768px)
```
Drawer Sidebar          |    1-Column Stack
Full-Width Content      |    Large Touch Areas
Simplified Tables       |    Mobile Menu
```

---

## 🎨 Color System

### Primary Gradient
```
┌─────────────────────────┐
│ #667eea (Indigo)        │ ← Primary Blue
│   ▼▼▼ Gradient ▼▼▼      │
│ #764ba2 (Violet)        │ ← Primary Dark
└─────────────────────────┘
```

### Status Colors
```
✓ Done     : #4caf50  [████ Green]
↻ Doing    : #ff9800  [████ Orange]
⚠ Blocked  : #f44336  [████ Red]
ℹ Info     : #2196f3  [████ Blue]
```

### Priority Indicators
```
HIGH    : #ff4081  [████ Pink/Red]
MEDIUM  : #ff9800  [████ Orange]
LOW     : #4caf50  [████ Green]
```

---

## 📐 Component Sizes

### Navbar
```
Height: 64px
├─ Logo: 40px
├─ Search: 48px
└─ Icons: 40px buttons
```

### Sidebar Item
```
Height: 48px
├─ Icon: 24px
├─ Label: 14px
└─ Padding: 12px vertical
```

### Card
```
Width: Responsive
Height: Auto
├─ Padding: 16px
├─ Border-radius: 8px
└─ Shadow: 0 1px 3px
```

### Button
```
Height: 44px
├─ Padding: 8px 16px
├─ Border-radius: 4px
└─ Font: 14px
```

---

## 📊 Page Layouts

### Login Page
```
                 Center
           ┌──────────────┐
           │              │
    Gradient│  AlphaWork   │
  Background│   [Logo]     │
           │              │
           │ [Email]      │
           │ [Password]   │
           │ [Remember]   │
           │              │
           │ [Login Btn]  │
           │              │
           │  Demo Creds  │
           │              │
           └──────────────┘
```

### Dashboard Page
```
Header
├─ Title: "Dashboard"
├─ Subtitle: "Welcome..."
└─ [New Project]

Stats Row (1x4)
├─ [Active Projects]
├─ [Open Issues]
├─ [In Progress]
└─ [Completed]

Projects Section
├─ Title: "Recent Projects"
└─ Grid (3 cols, 4 rows)
   ├─ [Card] [Card] [Card]
   ├─ [Card] [Card] [Card]
   ├─ [Card] [Card] [Card]
   └─ [Card] [Card] [Card]

Issues Section
├─ Title: "My Recent Issues"
└─ List
   ├─ [Row]
   ├─ [Row]
   ├─ [Row]
   └─ [Row]
```

### Kanban Board Page
```
Header
├─ Title: "Project Board"
├─ [New Issue]
└─ [View Options]

Board (Horizontal Scroll)
├─ Column 1: TODO (12)
│  ├─ [Card] ↕
│  ├─ [Card] ↕
│  ├─ [Card] ↕
│  └─ [+ Add]
├─ Column 2: DOING (7)
│  ├─ [Card] ↕
│  ├─ [Card] ↕
│  └─ [+ Add]
├─ Column 3: REVIEW (3)
│  ├─ [Card] ↕
│  └─ [+ Add]
└─ Column 4: DONE (23)
   ├─ [Card] ↕
   ├─ [Card] ↕
   ├─ [Card] ↕
   └─ [+ Add]
```

### Issues List Page
```
Header
├─ Title: "My Issues"
├─ Subtitle: "All issues assigned to you"
└─ [New Issue]

Filters
├─ [Search Bar]
├─ [Status Filter]
├─ [Priority Filter]
└─ [+ More Filters]

Table
├─ Headers: KEY | TITLE | STATUS | PRIORITY | ...
├─ Row 1: [Data]
├─ Row 2: [Data]
├─ Row 3: [Data]
├─ ...
└─ [Pagination]
```

### Project Detail Page
```
Project Header
├─ Title
├─ Description
├─ Status Badge
├─ Team Count
└─ [Edit] [⋮]

Tabs
├─ [Overview] ← Active
├─ [Board]
├─ [Team]
├─ [Settings]
└─ [Activity]

Tab Content (Overview)
├─ Statistics Section
│  ├─ [Total Issues]
│  ├─ [Completed]
│  ├─ [In Progress]
│  └─ [Blocked]
├─ Progress Section
│  ├─ [Overall: 75%]
│  ├─ [Sprint 1: 100%]
│  └─ [Sprint 2: 65%]
└─ Details Section
   ├─ [Project Key: PRJ]
   ├─ [Board Type: Kanban]
   ├─ [Lead: John Doe]
   └─ [Created: Jan 17, 2026]
```

---

## 🎭 Component Showcase

### Login Form
```
╔════════════════════════════╗
║      AlphaWork             ║
║        [Icon]              ║
║                            ║
║  [Email Input Box]         ║
║  [Password Input Box]      ║
║  ☐ Remember me             ║
║                            ║
║  [Sign In Button]          ║
║                            ║
║  Demo Credentials:         ║
║  Email: demo@...com        ║
║  Pass: Demo@123            ║
╚════════════════════════════╝
```

### Project Card
```
╔────────────────────────╗
│ [Gradient Header]      │  ← Indigo-Violet
│ Project Name           │
│ Kanban                 │  ← Board Type
├────────────────────────┤
│ Brief description of   │  ← 2-line max
│ the project here       │
│                        │
│ Issues: 12   Team: 4   │  ← Stats
│                        │
│ Progress: ████░ 65%    │  ← Progress Bar
│                        │
│ Status: ACTIVE         │  ← Status Badge
│                        │
│ ➜ View Project         │  ← Action
├────────────────────────┤
│ [View Project Button]  │
╚────────────────────────╝
```

### Kanban Card
```
┌──────────────────────┐
│ PRJ-123              │  ← Issue Key
│ Fix login button     │  ← Title
│ [Bug] [Frontend]     │  ← Labels
│ 👤 ⏰ 2d left        │  ← Meta
│ ████ (High)          │  ← Priority
└──────────────────────┘
```

### Table Row
```
┌────┬───────────────┬──────────┬──────────┬──────────────┐
│KEY │ TITLE         │ STATUS   │ PRIORITY │ ASSIGNEE     │
├────┼───────────────┼──────────┼──────────┼──────────────┤
│PRJ1│ Fix login bug │ REVIEW   │ HIGH     │ John Doe  ⋮  │
├────┼───────────────┼──────────┼──────────┼──────────────┤
│PRJ2│ Add feature   │ DOING    │ MEDIUM   │ Jane Smith ⋮ │
├────┼───────────────┼──────────┼──────────┼──────────────┤
│PRJ3│ Update docs   │ TODO     │ LOW      │ Bob J     ⋮  │
└────┴───────────────┴──────────┴──────────┴──────────────┘
```

---

## 🎯 Interaction Zones

### Clickable Areas
```
Navigation Items
├─ Sidebar Links (Full width)
├─ Nav Buttons (Icon buttons)
└─ Action Menus (⋮ icons)

Content Interactions
├─ Card Hover (Lift effect)
├─ Row Hover (Highlight)
├─ Drag Handles (Cursor change)
└─ Input Focus (Blue outline)

Buttons
├─ Primary (Color fill)
├─ Secondary (Color outline)
└─ Icon (Circle hover)
```

---

## 🌈 Visual Hierarchy

### Typography Hierarchy
```
32px → Titles (Dashboard, Project names)
↓
24px → Section headers (Recent Projects)
↓
16px → Card titles, Tab labels
↓
14px → Body text, Labels
↓
12px → Captions, Badges
```

### Color Hierarchy
```
Primary (#667eea)     → Active, Important
Neutral (#1a1a1a)     → Text, Content
Secondary (#666)      → Supporting
Tertiary (#999)       → Disabled, Hints
```

### Layout Hierarchy
```
Full width Header
↓
Fixed Sidebar + Flexible Content
↓
Card Grid / Table
↓
Detailed Content
```

---

## ✨ Animation Speeds

```
Quick:      0.1s (Hover, Icon)
Standard:   0.2s (Transition, Change)
Moderate:   0.3s (Open, Close)
Slow:       0.5s (Major transition)
```

---

## 📊 Grid System

### Desktop Grid
```
└─ 12 Columns (1400px max-width)
   ├─ 1/3 width = 340px (Project cards)
   ├─ 1/2 width = 450px (Stat cards)
   └─ Full width = 1400px (Tables)
```

### Gutter Size
```
Between items: 16px or 20px
Edge padding:  24px (desktop)
               16px (mobile)
```

---

## 🎬 User Flow

```
User Visits App
       ↓
[Not Logged In?]
       ├─ YES → Login Page
       │         ├─ Enter Email
       │         ├─ Enter Password
       │         └─ [Sign In]
       │              ↓
       │         [Success]
       │              ↓
       └─→ Dashboard
             ├─ [View Projects]
             ├─ [View Boards]
             ├─ [View Issues]
             └─ [Manage Project]
                      ↓
                 [Specific Page]
                      ↓
                [Take Action]
```

---

## 🎨 Design Tokens Summary

```
Colors
├─ Primary:    #667eea
├─ Dark:       #764ba2
├─ Success:    #4caf50
├─ Warning:    #ff9800
└─ Error:      #f44336

Spacing
├─ 4px   (xs)
├─ 8px   (sm)
├─ 12px  (md)
├─ 16px  (lg)
└─ 24px  (xl)

Typography
├─ H1: 32px, 600wt
├─ H2: 24px, 600wt
├─ Body: 14px, 400wt
└─ Caption: 12px, 500wt

Shadows
├─ Small:  0 1px 3px
├─ Medium: 0 4px 12px
└─ Large:  0 10px 40px

Radius
├─ Input: 4px
├─ Card: 8px
└─ Avatar: 50%
```

---

## 🏆 Design Excellence

✅ **Consistency** - Unified design language  
✅ **Clarity** - Clear visual hierarchy  
✅ **Accessibility** - WCAG compliant  
✅ **Responsiveness** - All screen sizes  
✅ **Performance** - Optimized rendering  
✅ **Usability** - Intuitive interactions  
✅ **Beauty** - Modern, professional look  
✅ **Functionality** - All features working  

---

This visual guide represents the complete AlphaWork UI design ready for implementation! 🎉
