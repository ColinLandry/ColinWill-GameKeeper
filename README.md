# Game Keeper
An app that allows you to keep track of your team, increasing coordination.

## Description
This is an app that allows a coach or team leader to modify and create teams, as well as schedule games.
Our app gives you the ability to message or email all of your team players at once, so you can keep everyone informed.
It also allows you to schedule games for two of your teams, allowing you to create calendar events based on the date you set.
It is a useful tool that will be able to help you manage your teams more efficiently.

## Preview Images
<img width="302" alt="teamspage" src="https://user-images.githubusercontent.com/32267705/39007975-5962736e-43d5-11e8-97cd-24180cd9250b.png">   <img width="302" alt="homepage" src="https://user-images.githubusercontent.com/32267705/39008017-7b273f84-43d5-11e8-9a33-2e10dc8dc292.png">

<img width="302" alt="gamespage" src="https://user-images.githubusercontent.com/32267705/39008136-d5c7880e-43d5-11e8-9981-38a27ea1fd33.png">   <img width="302" alt="teamdetails" src="https://user-images.githubusercontent.com/32267705/39008211-07fbc74a-43d6-11e8-9fb2-3d25a7b76422.png">

## HD Icon
<img alt="hdIcon" src="https://user-images.githubusercontent.com/32267692/39060967-d5c08a84-4490-11e8-9f99-20a4027de068.png">

## Featured Image
<img alt="featuredImage" src="https://user-images.githubusercontent.com/32267692/39060833-6d467b12-4490-11e8-93a3-081bcf74d44f.png">

## Git Config Instructions
`git config --global user.name` `<<<YOUR NAME HERE>>>`

`git config --global user.email` `<<<YOUR EMAIL HERE>>>`

## Commiting Instructions

Download/Clone the project from the repository

Perform the Git Config Instructions

Perform a `git branch -a` to see all the branches on the github repo

'git checkout staging' so that it creates a local copy of staging you can branch from

Create a branch that branches from staging and check it out `git checkout -b branchname`

When you are ready to put your work into staging (the main project) you do the following
Checkout staging `git checkout staging`

Pull staging (ensure your local staging is up to date with the copy on origin) 'git pull'

Checkout your branch `git checkout branchname`

Merge staging into your branch (This may cause a merge conflict if staging had content it downloaded) 'git merge staging'

Fix any issues that are in your project indicated by <<<<<<head ======== >>>>>>branchname

Once all issues are fixed perform another commit `git add .` and `git commit -m “”`
Checkout staging `git checkout staging`

Perform a git pull (to ensure staging did not change while we were making fixes) `git pull`

##  IMPORTANT (if there are no changes then we can proceed. If there are changes and updates are downloaded we repeat the above steps)

If no conflicts appeared and everything is up to date you are ready for your code to make its way into staging

Push your branch to github  `git push origin branchname`

Go to GitHub and perform a pull request (possibly tag a group member to get it done faster)
