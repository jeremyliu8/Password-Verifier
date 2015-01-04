password-verifier
=================

Liu, Jeremy. 2014

Purpose: 
Create a password verifier that checks for acceptable passwords based on a 5 rule criteria:
1. Must have at least 8 characters, and it must not contain any space character.
2. Must contain at least one special character, which is not a letter or digit.
3. Must contain at least one upper-case letter.
4. Must contain at least one lower-case letter.
5. Must contain at least one digit.

Features:
- Provides an easy to use GUI that allows user to enter a password and hit [enter] to check validity
- Displays strength of current password (based on 5 criteria)
- If password is unacceptable, display which criteria were broken
- Uses java regex matching to check each criteria
