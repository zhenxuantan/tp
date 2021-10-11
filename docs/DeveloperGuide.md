---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}
  
## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a CS2103T/CS2101 student
* has a need to keep track of tasks pertaining to CS2103T/CS2101 module
* has a need to keep track his/her CS2103T/CS2101 group mates' contact details
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Manage contacts of group mates and daily tasks faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                 | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new person               |                                                                        |
| `* * *`  | user                                       | delete a person                | remove entries that I no longer need                                   |
| `* * *`  | user                                       | find a person by name          | locate details of persons without having to go through the entire list |
| `* *`    | user                                       | hide private contact details   | minimize chance of someone else seeing them by accident                |
| `*`      | user with many persons in the address book | sort persons by name           | locate a person easily                                                 |
| `*`      | user                                       | Add a new task/deadline to the list | So that I can keep track of the tasks that needs to be done       |
| `* * *`  | new user                                   | Have an overview of my groupmate details like telegram, email and name.  | So I can easily contact them |
| `* * *`  | forgetful user                             | have a list of tasks           | So that I can follow up on it and not miss out tasks                   |
| `*`      | user                                       | delete a task/deadline in the list | So that I can keep delete the tasks that are already done          |
| `* * *`  | long term user                             | quickly check deadlines in order of priority (sort) | Clear the tasks due one at a time                 |
| `* * *`  | user                                       | filter the task according to the different modules | I know what I can do for each module               |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `SWEe-book` and the **Actor** is the `user`, unless specified otherwise)

**Use case (UC01): Delete a person**

**MSS**

1.  User requests to list persons
2.  System shows a list of persons
3.  User requests to delete a specific person in the list
4.  System deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. System shows an error message.

      Use case resumes at step 2.

**Use case (UC02): Add a person**

**MSS**

1.  User adds a person
2.  System shows the details of the person

    Use case ends.

**Extensions**

* 2a. The details are invalid or incomplete.

    * 2a1. System shows an error message.

      Use case resumes at step 2.

**Use case (UC03): Edit a person's particulars**

**MSS**

1. User requests to list persons
2. System shows a list of persons
3. User requests to edit a specific person in the list
4. System shows the details of the updated person

    Use case ends.

**Extensions**

* 3a. The details are invalid or incomplete.

    * 3a1. System shows an error message.

      Use case resumes at step 3.

**Use case (UC04): Find a person / a group of people**

**MSS**

1. User requests to find a person / a group of people using some keywords
2. System shows a list of persons pertaining to the keywords

   Use case ends.

**Use case (UC05): Add a task**

**MSS**

1. User keys in a task.
2. System shows the details of the task added to task list.

    Use case ends.

**Extensions**

* 2a. The task details are invalid or incomplete.
    * 2a1. System shows an error message about the incorrect or missing details.
    
    Use case ends.


**Use case (UC06): Delete a task**

1. User requests to list tasks.
2. System shows a list of tasks.
3. User keys in an index.
4. The task of specified index in task list is removed.
    
    Use case ends.

**Extensions**

* 1a. User keys in an invalid index.
    * 1a1. System displays an error message about invalid index.

  Use case ends.

**Use case (UC07): Have an overview of group mates' contact details**

1. User keys in a group of which its members' contact details are needed.
2. System displays the contact information of the group members of specified group

   Use case ends.

**Extensions**

* 1a. User keys in an invalid group.
    * 1a1. System displays an error message about invalid group.

  Use case ends.

**Use case (UC08): Have a list of tasks**

1. User keys in the command `listtasks`.
2. System displays the list of tasks.

   Use case ends.

**Extensions**

* 1a. User keys in an invalid command.
    * 1a1. System displays an error message about invalid command.

  Use case ends.

**Use case (UC09): Sort tasks**

1. User keys in the parameter (desc, due, added) and order (0, 1). 
2. System displays the tasks in the sorted order specified.

    Use case ends.

**Extensions**

* 1a. User keys in an invalid parameter or oder.
    * 1a1. System displays an error message about invalid parameter or order.

  Use case ends.

**Use case (UC10): Filter tasks**

1. User keys in a filter criterion.
2. System displays the tasks pertaining to the criterion specified.

    Use case ends.

**Extensions**

* 1a. User keys in an invalid criterion.
    * 1a1. System displays an error message about invalid criterion. Use case ends.


*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. System should respond within three seconds.
5. System still works even if the data file is missing.
6. If the data file is corrupted, the corrupted file is overwritten with an empty data file.
7. System will not collect any information from the user to abide by the Personal Data Protection Act.

*{More to be added}*

### Glossary

| **Term** | **Meaning** |
| ---- | --------| 
|**Mainstream OS**| Windows, Linux, Unix, OS-X |
|**Private contact detail**| A contact detail that is not meant to be shared with others|
|**CLI**|Command-Line Interface|
|**Group**| a group of either CS2103T or CS2101 students|
|**Index**| the ordering of task in the task list|

