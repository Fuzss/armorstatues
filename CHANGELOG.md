# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog].

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
