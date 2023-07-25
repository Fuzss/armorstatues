# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog].

## [v4.0.5-1.19.2] - 2023-07-25
### Added
- Added a button to the rotations screen for mirroring the current pose
### Changed
- Text fields now show description tooltips when hovered to make it more clear which field is for setting a new display name and which one changes the statue skin
- Opening the statue menu no longer requires a stick to be held, instead shift + right-click with an empty hand is the way to go, which the statue item tooltip reflects
### Fixed
- Fixed opening the armor stand configuration screen with a Fabric client on a server without the mod interacting with the armor stand server-side (e.g. removing equipment) 
- Fixed a rare network issue on servers when both Armor Statues and Straw Statues are installed

## [v4.0.4-1.19.2] - 2023-01-17
### Changed
- Opening the statue menu now requires a stick to be held in addition to sneaking, this was changed to improve compatibility with the Quark mod
- Removed the option to cycle through statue menu tabs using the tab key, it was conflicting with showing the vanilla server player list (at least on Fabric)
- Instead, you can now scroll through the tabs when your cursor is hovering them

## [v4.0.3-1.19.2] - 2023-01-06
### Fixed
- Fixed crash when trying to interact with entities using Fabric API 0.72.0+1.19.2

## [v4.0.2-1.19.2] - 2022-10-19
### Changed
- Any item can now be placed into the head slot on the equipment screen (thanks to [Mephodio])
- Tooltips on the rotations screen will no longer obstruct the armor stand model (thanks to [Mephodio])
- Tooltips on the style screen are now split into multiple lines to prevent them from flowing off the screen

## [v4.0.1-1.19.2] - 2022-10-15
### Changed
- Updated to Forge 43.1.40+ which is also now required
### Fixed
- Armor stand interactions are now properly disabled when trying to open the menu when the mod is only installed client-side

## [v4.0.0-1.19.2] - 2022-09-22
- Initial release

[Keep a Changelog]: https://keepachangelog.com/en/1.0.0/
[Mephodio]: https://github.com/Mephodio
