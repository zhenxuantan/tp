---
layout: page
title: Zhen Xuan's Project Portfolio Page
---

## **Project: SWEe-book**

### **Overview**

SWEe-book is a desktop application used for contact and task management pertaining to CS2103T and CS2101 module. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

**Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=CS2103T-W12-2&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&tabAuthor=zhenxuantan&tabRepo=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&zA=zhenxuantan&zR=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&zACS=198.05172413793105&zS=2021-09-17&zFS=CS2103T-W12-2&zU=2021-11-05&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false&until=2021-11-05)

### **Enhancements to existing features**

* Improved Ui for contact list and added Ui for task list [#48](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/48)
* Removed all references to pre-existing project ([AB-3](https://se-education.org/addressbook-level3/)) [#127](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/127)
* Wrote tests for the internal features like Task, Group, TaskList and Date [#132](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/132)

### **New Features added**

#### **Added the ability to sort the tasks in 8 different ways.** [#38](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/38), [#46](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/46), [$50](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/50)

* What it does: allows the user to sort the tasks based on their description, group, priority, and (due) date in two different orders: ascending order and descending order.
* Justification: This feature allows the user to navigate through their tasks easily, giving them a broad overview of what is important or urgent instead of having to scroll through the whole task list.
* Highlights: Reflected changes in Ui and allowed users to perform other commands like delete task while having the task list sorted, it also allows potential future commands like sorting and filtering simultaneously or sorting via multiple variables to be implemented easily.

<div style="page-break-after: always;"></div>

#### **Added the ability to add priorities to tasks.** [#64](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/64)

* What it does: allows the user to add priorities to their tasks which includes high, medium or low priorities.
* Justification: This feature allows the user to recognise the importance of their tasks without having to read the whole task list.
* Highlights: Allowed the level of priority to change the colour of the alert in the Ui, utilising code to change the Ui based on the type of priority of the task.

#### **Allowed date, priority and recurring frequency to be optional.** [#64](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/64)

* What it does: allows users to add tasks without having to type date (todo task only), priority or recurring frequency.
* Justification: This feature allows the user to add tasks without the hassle of typing too many parameters by making tasks having the default priority of medium and default recurring frequency of none.
* Highlights: This enhancement allows future parameters or fields to be optional as well.

### **Project Management**

* Merged 28 out of 79 successfully merged pull requests into the team's repository

### **Documentation**

#### **User Guide**

* Added documentation for `sortTasks` command
* Added documentation for `priority` feature
* Added screenshots for `sortTasks` and `filterTasks` command

#### **Developer Guide**

* Added documentation for `sortTasks` feature and how it links between different components: Logic, Model and Ui
* Updated the screenshots to suit SWEe-book
* Added use cases

### **Community**

* Helped to spot bugs in Practical Examinations - Dry Run (PE-D) See [here](https://github.com/zhenxuantan/ped/issues)
