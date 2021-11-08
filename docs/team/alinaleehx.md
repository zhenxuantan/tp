---
layout: page
title: Alina Lee's Project Portfolio Page
---
### Project: SWEe-book

SWEe-book is a desktop application used for contact and task management pertaining to CS2103T and CS2101 module. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* **Enhancements to existing features**:
    * Update test data for tasks. [#47](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/47)
    * Add additional tests. [#66](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/66)

* **New Feature**: Added internal features like tasks, group, taskList, storage for tasks and more to support task management in the app SWEe-book. [#5](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/5)
    * What it does: Created the internal features necessary for all task commands and task management in SWEe-book.
    * Justification: This allows for development of features related to task management and is hence essential.
    * Highlights: All future commands related to task management relies on this.

* **New Feature**: Add the ability to add 3 different types of tasks. [#5](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/5) [#42](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/42)
    * What it does: allows the user to add 3 different types of tasks, namely todo, event and deadline specifying date, group and task description.
    * Justification: This feature allows the user to add and keep track of the tasks that needs to be done for each group by a certain date.
    * Highlights: This allows 3 different types of tasks which likely covers most user's needs for task types, and
      provides the base for other future features like sorting and filtering tasks and is a basic but integral part of task management in SWEe-book.

* **New Feature**: Added the ability to delete a task. [#39](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/39)
    * What it does: allows the user to delete a task already present in the task list, namely by keying the index of that task to be deleted.
    * Justification: This feature improves the product significantly because a user can make mistakes when using addTask
      command and this feature provides a convenient way to rectify these mistakes by allowing the user to delete the tasks that are added by mistake or no longer needed.
    * Highlights: This allows users to delete tasks and is a key feature of task management in SWEe-book.

* **New Feature**: Added the ability to mark a task as done. [#69](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/69) [#123](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/123)
    * What it does: allows the user to mark a task already present in the task list as done, namely by keying the index of that task to be marked as done.
    * Justification: This feature allows the user to keep track of which tasks are done or not yet done.
    * Highlights: Task List UI is updated synchronously when task is marked as done.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=alinaleehx&tabRepo=AY2122S1-CS2103T-W12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
    * Reviewed and successfully 3 pull requests into the team's repository. [#122](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/122) 
      [#73](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/73) [#67](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/67)

* **Documentation**:
    * User Guide:
        * Added documentation for `addTask` command [#47](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/47)
        * Added documentation for `deleteTasks` command [#47](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/47)
        * Added documentation for `doneTasks` command [#69](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/69)
        * Did cosmetic tweaks to existing documentation of features `help` [#125](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/125)
        * Added UI mockup for initial user guide [#20](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/20)

    * Developer Guide:
        * Added documentation for `addTasks` command and `deleteTasks` command [#47](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/47)
        * Added documentation for `doneTasks` command [#69](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/69)
          [#149](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/149)
          [#151](https://github.com/AY2122S1-CS2103T-W12-2/tp/pull/151)

* **Community**:
    * Helped to spot bugs in Practical Examinations - Dry Run (PE-D) [Issues](https://github.com/alinaleehx/ped/issues)
