---
layout: page
title: Chen Yuan's Project Portfolio Page
---

### Project: SWEe-book

SWEe-book is a desktop application used for contact and task management pertaining to CS2103T and CS2101 module. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.


* **Enhancements to existing features**:
  * Removed address and tag fields from Person as they were irrelevant to our product.
  * Added Telegram And Github, Group fields into Person.
  * Refactored the `add` and `edit` command to reflect both of the above changes
  
* **New Feature**: Added the ability to edit tasks.
  * What it does: allows the user to choose a task from the list in the UI, and edit any number of fields of this task.
  * Justification: This feature removes the hassle of the need to delete and add a new task, just to change one field of a task.
  * Highlights: Wrote tests to cover the new feature

* **New Feature**: Added the ability to filter people by group.
  * What it does: allows the user to filter the list of people by group (CS2103T or CS2101)
  * Justification: This feature allows the user to have an overview of their groupmates from a specific group, should there be a need to declutter the contact list.
  * Highlights: Refactored and add new tests to cover the deleted (address and tag) and newly added fields (github and telegram and group).

  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=cyyeu&tabRepo=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed releases `v1.1` - `v1.2` (2 releases) on GitHub
  * Merged 21/75 pull requests
  * Set up GitHub repo and Codecov
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features `editTask` command
    * Update documentation for the refactored `add` and `edit` commands
  * Developer Guide:
    * Added implementation details of the `edit` feature.
    * Updated documentation for the model component to reflect latest changes
    * Add use case for `editTask` feature

* **Community**:
  * Helped to spot bugs in Practical Examinations - Dry Run (PE-D) [Issues](https://github.com/cyyeu/ped/issues)
