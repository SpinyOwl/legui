# Contribution

## Asking a question

Questions about how to use LEGUI could be asked in Discord channel **[legui-discussion](https://discord.gg/6wfqXpJ)**

Questions about the design or implementation of LEGUI or about future plans should be asked in **[legui-develop](https://discord.gg/SwRmRt9)** channel

Please don't open a GitHub issue to discuss design questions without first checking with a maintainer.

## Reporting a bug

If LEGUI is behaving unexpectedly at run-time or crashing - option to create issue.

For bugs where it makes sense, a short, self contained example is absolutely invaluable. 
Just put it inline in the body text. 
Note that if the bug is reproducible with one of the test programs that come with LEGUI, just mention that instead.

Don't worry about adding too much information. Unimportant information can be abbreviated or removed later, 
but missing information can stall bug fixing, especially when your schedule doesn't align with that of the maintainer.

Please provide text as text, not as images. This includes code, error messages and any other text. 
Text in images cannot be found by other users searching for the same problem and may have to be re-typed by maintainers when debugging.

You don't need to manually indent your code or other text to quote it with GitHub Markdown; just surround it with triple backticks:

```
Some quoted text.
```
You can also add syntax highlighting by appending the common file extension:

```java
public static void main(String ... args)
{
  // some code
  return;
}
```
If your bug is already reported, please add any new information you have, or if it already has everything, give it a 👍.

## Requesting a feature

Please explain why you need the feature and how you intend to use it. 
If you have a specific API design in mind, please add that as well. 
If you have or are planning to write code for the feature, see the section below.
If there already is a request for the feature you need, 
add your specific use case unless it is already mentioned. If it is, give it a 👍.

## Contributing a bug fix

>**Note:** You must have all necessary [intellectual property rights](https://en.wikipedia.org/wiki/Intellectual_property) to any code you contribute.
>If you did not write the code yourself, you must explain where it came from and under what license you received it. 
>Even code using the same license as LEGUI may not be copied without attribution.

**There is no preferred patch size.** A one character fix is just as welcome as a thousand line one,
if that is the appropriate size for the fix.

In addition to the code, a complete bug fix includes:
* Change log entry in README.md, describing the incorrect behavior
* Credits entries for all authors of the bug fix
Bug fixes will not be rejected because they don't include all the above parts, 
but please keep in mind that maintainer time is finite and that there are many other bugs and features to work on.

If the patch fixes a bug introduced after the last release, it should not get a change log entry.

If you haven't already, read the excellent article [How to Write a Git Commit Message.](https://chris.beams.io/posts/git-commit/)

## Contributing a feature

>**Note:** You must have all necessary rights to any code you contribute. 
>If you did not write the code yourself, you must explain where it came from and under what license. 
>Even code using the same license as LEGUI may not be copied without attribution.

>**Note:** If you haven't already implemented the feature, 
>check first if there already is an open issue for it 
>and if it's already being developed in an experimental branch.

**There is no preferred patch size.** A one character change is just as welcome
as one adding a thousand line one, if that is the appropriate size for the feature.

In addition to the code, a complete feature includes:

Change log entry in README.md, listing all new symbols
* News page entry, briefly describing the feature
* Guide documentation, with minimal examples, in the relevant guide
* Reference documentation, with all applicable tags
* Cross-references and mentions in appropriate places
* Credits entries for all authors of the feature

If you haven't already, read the excellent article [How to Write a Git Commit Message.](https://chris.beams.io/posts/git-commit/)

Features will not be rejected because they don't include all the above parts,
but please keep in mind that maintainer time is finite and that there are many other features and bugs to work on.

Please also keep in mind that any part of the public API that has been included in a release 
cannot be changed until the next major version.
