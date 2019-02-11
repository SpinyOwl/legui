## [1.5.0]

## [1.4.8]
### Added
- Added generic type support for SelectBox. 

## [1.4.7]
### Updated
- Updated selectbox animation logic - so if number of elements is less than visible count - selectbox popup will be smaller.
### Fixed
- Fixed code inspection issues. 

## [1.4.6]
### Fixed
- Huge memory leak during rendering shadows.

## [1.4.5]
### Added
- CSS-like styles
- Display types: FLEX, NONE and MANUAL.
- Style options:
  - display
    - NONE
    - FLEX
    - MANUAL
  - position
    - ABSOLUTE
    - RELATIVE
  - flexStyle
  - background
  - border
  - font
  - borderTopLeftRadius
  - borderTopRightRadius
  - borderBottomRightRadius
  - borderBottomLeftRadius
  - width
  - height
  - minWidth
  - minHeight
  - maxWidth
  - maxHeight
  - paddingTop
  - paddingBottom
  - paddingRight
  - paddingLeft
  - marginTop
  - marginBottom
  - marginRight
  - marginLeft
  - top
  - bottom
  - right
  - left
- FLEX style options:
  - flexDirection
  - justifyContent
  - alignItems
  - flexWrap
  - alignContent
  - alignSelf
  - flexGrow
  - flexShrink
  - flexBasis
### Fixed
- Fixed javadoc issues.
- Fixed issue with rendering empty text area.

### Changed
- Updated animations for scrollable panel and scrollbar

## [1.4.4]
### Added
- Moved demo classes to repo.
- Added layout system.
  - Added border layout.
  - Added box layout.
### Fixed
- Added OpenGL version checking in renderer.
- Fixed default initializer.
- Use simple point in axis-aligned rectangle test.
### Changed
- Changed context api to frame buffer size (now returns `Vector2i` instead of `Vector2f`).
- Changed api to child components of component (was `getChilds()` - now `getChildComponents()`).

## [1.4.3]
### Fixed
- Fixed nvg password input renderer bug (with empty password rendering).
 
## [1.4.2]
### Major
- Updated component structure to DOM-like. More information you can find in commits.
 
## [1.4.1]
### Fixed
- Fixed issue with ToggleButton renderer.
 
## [1.4.0]
### Major
- Added class bindings which describe how object should be marshalled.
- Added bindings for several types.
 
### Changed
- Recreated json marshaller according to new binding system.

### Removed
- Removed old marshalling system.

## [1.3.6]
### Fixed
- Fixed component `addAll(Collection<? extends Component> components)` method - no more duplicated compoents.

## [1.3.5]
### Fixed
- Updated renderer to discard rendering of too small components.  

## [1.3.4]
### Fixed
- Fixed issue with adding tab in disabled text area.

## [1.3.3]
### Fixed
- Updated readme and build script.

## [1.3.2]
### Changed
- Removed generic from component. [fe26a6a](https://github.com/LiquidEngine/legui/commit/fe26a6a716dc7f0c8e5ba105fc8da9ca289e7c6d)
- Fixed tooltip component. [ddf43ea](https://github.com/LiquidEngine/legui/commit/ddf43eae2d5c57bb4e10cad5ae941cc7f366114d)
- Updated tooltip javadocs. [b3d7e4f](https://github.com/LiquidEngine/legui/commit/b3d7e4f5a425a6ff27f6b90fd0b966579954cddb)
- Removed lamda method 'renderInScissor' in rendering utilities. [3d99486](https://github.com/LiquidEngine/legui/commit/3d99486300f6b998287251c49e7f58d09b108fae)
### Major 
- Added tabbing [#2](https://github.com/LiquidEngine/legui/issues/2) as additional key event listener. [eac5f4a](https://github.com/LiquidEngine/legui/commit/eac5f4a6e31e6644aa64e259f0f6f7c48c6f33cd )
- Added `TAB` key callback for text area - adds 4 spaces. [19f6ca7](https://github.com/LiquidEngine/legui/commit/19f6ca78f615f9560ef267d2b31bb4c66011984f)
- Updated text area renderer to support `\t` symbol. [326a083](https://github.com/LiquidEngine/legui/commit/326a0837321bf74ba21d15520c0352ab0faca5a7)
- Added focus event generation on switching between components using tab. [811a4b2](https://github.com/LiquidEngine/legui/commit/811a4b2e50876590689e327a0640662fa87b3c00)
### Fixes 
- Fixed new created issues with marshallers

## [1.3.1]
### Changed
- Removed parameter type from component and updated related systems.

## [1.3.0]
### Changed
- Removed `Controller` and `Container` classes
- Component is by default container.
- Updated related systems (rendering, marshaling, etc.)

## [1.2.7]
### Fixed
- Fixed text rendering bug (with rendering empty string)

## [1.2.6]
### Changed
- Added contribution guide.
- Added license.
- Updated readme.
### Fixde
- Added missed padding in text rendering utility.

## [1.2.5]
### Changed
- Added javadoc for several classes.

### Fixed
- Removed unused imports.
- Performance fix on rendering __'out of bound'__ components.
- Refactoring.

## [1.2.4]
### Changed
- Fixed all memory leaks.
- fixed tests.
### Fixed
- Added javadocs for SystemEventProcessor class.
- Added javadocs for Context class.

## [1.2.3]
### Changes
- Extracted White Theme inner classes to separate classes.

## [1.2.2]
### Changes
- Added clipboard class.
- Removed window pointer usage in event listeners for text components. 

## [1.2.1]
### Changes
- Added several component events and listeners for them:
  - CheckBoxChangeValueEvent
  - ScrollBarChangeValueEvent
  - SelectBoxChangeSelectionEvent
  - SliderChangeValueEvent
  - TextInputContentChangeEvent
  - WidgetCloseEvent
- Updated LWJGL version to `3.1.4-SNAPSHOT`
- Renamed component method from `getScreenPosition()` to `getAbsolutePosition`. 
- Updated `NvgComponentRenderer` so that now all component renderers inherited from it checking visibility of rendered component in the parent 
components (to skip rendering of hidden components).

## [1.2.0]
### Changes
- Removed event processor from context.
- Event processors moved to another package.
- Some changes in nvg renderer structure.
- Removed renderer provider from constructor.
  
## [1.1.10]
### Fixed
- Fixed issue with widget minimize button.

## [1.1.9]
### Fixed
- Fixed some mistakes with generics diamonds.

## [1.1.8] - 2017-08-29
### Fixed
- Fixed issue with widget title height.
- Fixed issue with backspace and delete action on cleared TextInput and TextArea with `setText("")` method.
- Fixed TextArea key event listener (can't select all text in non-editable TextArea).

## [1.1.5] - 2017-08-25
### Fixed
- Removed debug option from NvgRenderer.
- Removed states from nvg renderers.
- Fixed nvg text renderers.
- Fixed issue with ImageIcon (there was used LoadableImage when should be Image).
- Added some javadocs.
