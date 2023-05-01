# BA Races
Utilities for hosting Barbarian Assault races

This plugin was made to assist with hosting Casual BA races, which are held every Sunday. 
If you're not familiar, you can read more about races [here](http://races.casualba.site). 

The side panel has all the functionality you need to host and track your races. 
You'll find it under the black-and-white racing flag icon: 
// TODO

There are several phases that the plugin defines over the course of a race. 
This helps automate each step of the process. 
Ensure that you're in the expected phase at each point described below. 

## Setting up a race
You should set up all team information before starting a race. 
Also make sure that all RSNs are spelled correctly if you're manually entering names. 
If one or more RSNs are misspelled, you may lose the ability to track some teams. 

### Captain phase
Generally, race hosts pick captains and let them draft their own teams. 
After calling up your captains, you can start a new team with each player by shift + right-clicking on them and 
choosing "Captain".
You can also manually add team captains in the side panel, ensuring their RSNs are spelled correctly. 
If some captains are unable to come forward, you can remove their team with the "x" button in the top right, 
or you can manually edit the RSN to the new captain if a replacement comes forward. 

### Draft phase
After the captains are finalized, click the "Begin draft" button to enter the draft phase. 
In the draft phase, players are added to existing teams only; 
if you would like to form a rebel team, you can do so once the teams are drafted (more on this later). 
Note that you will automatically enter draft phase if you begin manually entering RSNs beyond the captain. 
If this was unintentional, you can remove any non-captain RSNs, and you'll be back in captain phase. 
During draft phase (and the next pre-race phase), if any players added to the draft, including captains, are not 
present in the wave 1 lobby, a yellow "!" will appear next to their name. 

### Entering drafted players
Before adding any players, check that the order of captains from the top of the panel matches the draft order. 
If there are 8 or fewer teams, and the captains are standing in a horizontal line, you'll 
notice incorrect team positions in the side panel with a red "!" icon next to the captain's name. 
You can move each team placement with the up or down arrows in the top right of each team card. 

With this set up, you'll be able to add players to the draft by shift + right-clicking them and choosing "Draft". 
This places them in the next available spot as determined by the draft style in the plugin settings. 
A snake draft is assumed by default, so if, for example, there are three teams, the draft will follow this pattern: 

notated `#(team number)-(number of picks)`: 
#1-1, #2-1, #3-2, #2-1, #1-2, #2-1, and so on. 

If you're using a custom draft order, you'll need to manually enter the RSNs for each team. 
You can shift + right-click to copy that player's RSN to the clipboard to reduce the chance of misspelling. 

#### Rebels
If some players want to form a rebel team outside the draft, a "rebel" button will show up below the teams once all 
drafted teams have reached 5 players. 
You'll be able to add players to this team by shift + right-clicking them and choosing "Rebel". 
If there are multiple rebel teams, enter the entire team before creating the next rebel team. 
The order of entered players in this case doesn't matter since rebels don't follow the draft, but the first RSN 
in the panel is considered the de facto captain. 

## Starting a race

### Pre-race phase
You should click the "End draft" button once all the teams are finalized, including rebels.
This doesn't start the race, but it will prevent adding any more teams.
Once the teams are prepared, the scroller of each team should be waiting by the ladder, while the rest of the team
stands in the top right corner of wave 1. 
If this is the case, a green checkmark will appear by each team member.
Note that some racers may log into a different account, in which case you should update their names in the side panel.
You can still change their names during or after the race, but doing this now ensures that the teams will be tracked 
accurately.

It's recommended to use a hotkey to initiate the countdown to start, but you can also press the "Start countdown" 
button in the side panel (**note**: if you're sending a chat message to start, make sure that the game client is 
focused by clicking somewhere in it, e.g., your inventory, otherwise you may send it late, in which case, you should 
restart). 
By default, the countdown will be 3 ticks, and each tick elapsed will produce a game message. 
This makes it so your chat message or emote input can be submitted at game message "1...", 
and other players will see your signal at game message "Go!", which starts the race timer on the next tick, 
as this would be the precise tick on which teams enter wave 1 if they start immediately. 

### False starts and restarts
The plugin will automatically detect false starts, which is when a team starts too early. 
The timer will not begin if this happens, and a warning game message will notify you of this. 
You should tell your team to come back to wave 1 if there's a false start or some other mishap. 
Regardless of whether your race begins with a false start, you can reset a started race in the side panel. 
This will return to the pre-race phase where you can begin the countdown again. 

### Tracking racers
The plugin records times for each team based on the appearance and disappearance of its members within each wave lobby. 
Therefore, it's important to be following the majority of the teams as they progress through the waves. 
The round time is the most important duration to record, so you should be in the position depicted below once the 
first team has entered wave 10: 
(TODO)

If teams are appearing around the same time in waves 4 and 5 or waves 8 and 9, there is an optimal column to stand in, 
as highlighted below. 
It's within 15 tiles of the ladders into these waves. 

Times are displayed in team overlays as they're recorded. 
In the event that the team could not be detected going into or out of a wave, a '?' will display for that wave. 

## Ending a race
The race will automatically end the "in progress" phase when the last team is detected as finished. 
You may also manually end a race in case some teams weren't detected or if any teams DNF (did not finish). 
Note that you cannot undo ending a race; once you press "finish", the race is over. 
Upon ending the race, the data for each team and their times will be exported. 
A summary of the results will be displayed in the side panel. 

### Exported data
By default, race data is exported as a csv file in your RuneLite folder, under "ba-races". 
The file will be named after the date and time when the race was started, in format `YYYY-MM-DD-HH-MM`. 
The first 5 columns will be the team RSNs in draft order (if applicable), then the total round time, then the position, 
then the wave times from waves 1-10, then additional notes.
The rows will be ordered from the fastest team to the slowest. 

## Additional configuration
The following are found in the RuneLite plugin settings panel: 

TODO