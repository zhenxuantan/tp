---
layout: page
title: User Guide
---

SWEe-book is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SWEe-book can get your contact management and task management done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `SWEe-book.jar` from [here](https://github.com/AY2122S1-CS2103T-W12-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your SWEe-book.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe g/CS2103T p/98765432 e/johnd@example.com tg/johndoe gh/johndoe` : Adds a contact named `John Doe` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `type/TYPE [date/DATE]` can be used as `type/deadline [date/2021-09-11]` or as `type/deadline`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME g/GROUP p/PHONE_NUMBER e/EMAIL tg/TELEGRAM_USERNAME gh/GITHUB_USERNAME`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Usernames can optionally be prepended with '@'. SWEe-book takes care of it!
</div>

Examples:
* `add n/John Doe g/CS2103T p/98765432 e/johnd@example.com tg/@johndoe gh/johndoe`
* `add n/Betsy Crowe p/92221234 g/CS2101 e/betsycrowe@example.com tg/betsyyy gh/crowebetsy`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [g/GROUP] [p/PHONE] [e/EMAIL] [tg/TELEGRAM] [gh/GITHUB]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding a Task : `addtask` [coming soon]

Adds a new task (determined by what is inputted for type/TYPE) with task description as specified by d/DESCRIPTION 
for the group specified by g/GROUP.

Format: `addtask d/DESCRIPTION g/GROUP type/TYPE [date/DATE]`
* `GROUP` refers to one of the 2 groups: CS2101 or CS2103T
* `TYPE` refers to one of the 3 types of tasks: todo, event or deadline

Examples:
* `addtask d/Project meeting g/CS2103T type/todo`
* `addtask d/Presentation 1 g/CS2101 type/deadline date/2020-11-02`
* `addtask d/Mock QnA 1 g/CS2101 type/event date/2020-10-02`

### Deleting a task/deadline: `deletetask` [coming soon]

Format: `deletetask index`

Deletes the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index must be a positive integer 1, 2, 3, …

Examples:
* `deletetask 1 deletes the 1st task or deadline in the task list.`

### Retrieving contact information of a group: `socials` [coming soon]
Retrieves contact information of groupmates by the specified `g/GROUP`.
This includes their Github, Telegram, Email, etc..

Format: `socials g/GROUP`
* `GROUP` refers to one of the 2 groups: CS2101 or CS2103T

Examples:
* `socials g/CS2103T` returns contact information of groupmates in CS2103T
  * Output: (Note: the icons are clickable in the SWEe-book app.)
    * Alina <img src="images/github.png" width="25" height="25"/> <img src="images/tele.jpeg" width="25" height="25"/> <img src="images/mail.png" width="25" height="25"/>
    * Chen Yuan <img src="images/github.png" width="25" height="25"/> <img src="images/tele.jpeg" width="25" height="25"/> <img src="images/mail.png" width="25" height="25"/>
    * Zhen Xuan <img src="images/github.png" width="25" height="25"/> <img src="images/tele.jpeg" width="25" height="25"/> <img src="images/mail.png" width="25" height="25"/>
    * Ambrose <img src="images/github.png" width="25" height="25"/> <img src="images/tele.jpeg" width="25" height="25"/> <img src="images/mail.png" width="25" height="25"/>
    * Joseph <img src="images/github.png" width="25" height="25"/> <img src="images/tele.jpeg" width="25" height="25"/> <img src="images/mail.png" width="25" height="25"/>
  * `socials g/CS2101` returns contact information of groupmates in CS2101  

### Sorting tasks: `sort` [coming soon]
Sort tasks based on their description or their deadlines (chronologically, or the reverse), or by time added.

Format: `sort p/PARAMETER o/ORDER`
* The sort is case-insensitive. e.g CS2030 will be lexicographically identical to cs2103
* PARAMETER includes desc (for description), due (for deadline / time of event), and added (for date added)
* ORDER includes 0 for ascending order (0-9 and A-Z, oldest to newest) and 1 for descending order(Z-A and 9-0 / newest to oldest)
* For tasks with no due dates, they are always at the back of any sort

Examples:
If the following is in the task list:
| Type        | Description | Date & Time     |
| ----------- | ----------- | --------------- |
| `Todo`      | user guide  |        -        |
| `Deadline`  | quiz        | 25-09-2020 2359 |
| `Event`     | test        | 21-09-2020 2359 |
| `Event`     | exam        | 22-09-2020 2359 |

* `sort p/desc o/1` returns tasks with the following descriptions  `user guide`, `test`, `quiz`, `exam` 
* `sort p/due o/1` returns returns tasks with the following descriptions   `test`, `exam`, `quiz`, `user guide`

### Filtering tasks by modules: filter [coming soon]
Filter tasks based on the different modules (either CS2101 or CS2103T)

Format: `filter g/Group`
* Filters the task by the specified `g/Group`
* `g/Group` refers to one of the 2 groups: CS2101 and CS2103T
* `g/Group` must be either of the 2 groups stated above
* Tasks corresponding to the group specified will be shown

Examples:
* `filter CS2101` shows all the tasks related to CS2101 group
* `filter CS2103T` shows all the tasks related to CS2103T group

### Listing all tasks: listtasks [coming soon]
Shows a list of all tasks in the address book. Optional argument to fill in the day that the tasks are due.

Format: `listtasks [date/DATE]`
* `DATE` will be in `YYYY-MM-DD` format
* Lists tasks that are not past the date that is attached to them (pending tasks)
* Can query for tasks were on a past date

Examples:
* `listtasks date/2020-11-02` returns all tasks on 02 Nov 2020
* `listtasks` returns all tasks

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
**Add Task (coming soon)** | `addtask d/DESCRIPTION g/GROUP type/TYPE [date/DATE]`
**Delete Task (coming soon)** | `deletetask index`
**Socials (coming soon)** | `socials g/GROUP`
**Sort Tasks (coming soon)** | `sort p/PARAMETER o/ORDER` <br> e.g. `sort p/desc o/1`
**Filter Tasks (coming soon)** | `filter g/Group`
**List Tasks (coming soon)** | `listtasks [date/DATE]`
