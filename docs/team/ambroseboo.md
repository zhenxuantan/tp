---
layout: page
title: Ambrose's Project Portfolio Page
---

### Project: SWEe-book

SWEe-book is a desktop application used for contact and task management pertaining to CS2103T and CS2101 module. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* **Enhancements to existing features**:
    * Implement listTasks to reflect in GUI, so that both contacts and tasks are simultaneously displayed [#49](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/49)

* **New Feature**: Added the ability to list all tasks added. [#38](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/38), [#46](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/46), [$50](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/50)
    * What it does: Allows the user to list all tasks that were previously added to the project.
    * Justification: This feature allows users to view their added tasks, and serves as a start point fetch data for filterTasks and also the tasks appearing on the UI on startup. 
    * Highlights: Upon the editing of the UI to include the task section, the tasks are reflected in the GUI rather than the CLI.

* **New Feature**: Added the ability to add recurring frequencies to tasks. [#64](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/64)
    * What it does: Allows the user to add recurring frequencies to their tasks which can be weekly, monthly, or yearly frequencies. For weekly, 
      the tasks are repeated on the same day of the week, for monthly the tasks are repeated by the date (DD) every month, for yearly the tasks are repeated 
      by the DD/MM every year.
    * Justification: This feature allows the user to add tasks that can repeat themselves, so that the tasks can update themselves automatically.
    * Highlights: The tasks are updated on the startup of the application, ensuring they are always updated whenever the user opens the application.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=CS2103T-W12-2&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=zoom&tabAuthor=zhenxuantan&tabRepo=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&zA=ambroseboo&zR=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&zACS=198.05172413793105&zS=2021-09-17&zFS=CS2103T-W12-2&zU=2021-11-07&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false&until=2021-11-07)

* **Project management**:
    * Merged and fixed style errors on pull request #5 into the team's repository 

* **Documentation**:
    * User Guide:
        * Added initial documentation for `addTask`, `deleteTask`, `listTasks`, `filterTasks`, `sortTasks`
        * Added full documentation for `listTasks`
        * Updated command summary
        * Updated documentation for `addTask` (explain recurring field)
    * Developer Guide:
        * Added documentation for `listTasks` command
        * Added documentation for `recurring` feature in `Task`

* **Community**:
    * Helped to spot bugs in Practical Examinations - Dry Run (PE-D) [Issues](https://github.com/ambroseboo/ped/issues)
