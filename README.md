# LogAutomation
 The program imports an email log into a log analysis system, which only accepts logs in JSON format.
 ● The script combines events based on the session id (column 2):
      ○ Events may be happening in parallel and overlapping.
      ○ Incomplete sessions (missing any of the fields) should be ignored.
 ● The Script calculates the duration of each session.
 ● Prints out all of the combined events in a JSON formatted array (output.json).
