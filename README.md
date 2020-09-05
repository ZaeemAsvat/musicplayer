# Smart Music Player

A Smart Local Music Player Application For Android (Work In Progress)

Current application state and features:
- basic but neat-looking GUI for viewing and playing audio files stored on the user's device
- smart, dynamic shuffling mode based on K-Means Clustering on MFCC features extracted from the audio files. In this mode, tracks are dynamically selected based on user interaction and feedback throughout the shuffling mode period.

Next Features To Be Explored:
- Smart, dynamic shuffling using more sophisticated clustering algorithms than K-Means as well as experimenting with other content-based features (spectral centroid, zero-crossing, etc)
- Creating personalized playlists unique to each user - learning-to-rank methods
- Categorizing tracks according to moods such as happy, sad, calm and energetic using classificstion methods - neural networks, logistic regression, decision trees, etc.
- Improving GUI, and adding more activities and functionality that lets users create their own playlists, and creating pre-defined playlists based on artist, genre, album, etc 

Note that this project is in early stages and the key features are still being implemented amd experimented with, therefore the application is not in the most stable state at this point in time. However progress is being made regularly and this repo will be updated accordingly.

This project is being implemented using Java in android studio, and all machine learning techniques have been applied using the WEKA library.
