# ITourist App

ITourist is a comprehensive travel guide application designed to enhance the experience of tourists by making it easier to explore attractions, plan routes, and connect with a travel community. The app integrates real-time updates and provides personalized recommendations based on user behavior.

## Features

- **Tourist Attraction Discovery**: Easily search for attractions, including museums, restaurants, and other popular places.
- **Recommendation System**: Personalized suggestions based on user behavior and feedback.
- **Community Interaction**: Users can share travel experiences and connect through a dedicated travel community.
- **Route Mapping**: Provides the best routes on a map for easy navigation.
- **Tour Guide Booking**: Users can request local guides for cultural insights and assistance.
- **Favorites and History Tracking**: Keeps track of user-favorited attractions and a history of visited places.
- **User Authentication**: Secure login, registration, and OTP-based verification using Firebase Authentication.
- **Real-Time Chat**: Connect with other travelers using real-time chat powered by Firebase Realtime Database.
- **Data Synchronization**: Stores user preferences, favorites, and chat data in Firestore for real-time synchronization.

## Architecture

The ITourist app follows **MVVM (Model-View-ViewModel)** and **Clean Architecture** principles, ensuring scalability, maintainability, and clear separation of concerns.

### Key Technologies Used:
- **Firebase Authentication**: Handles user login, registration, and OTP-based verification.
- **Firestore Database**: Stores user-generated data like favorites, recommendations, and chat history.
- **Realtime Database**: Manages real-time chat messages for seamless communication.
- **Room Database**: Provides offline caching for selected features.
- **Multiple APIs**: Fetches attractions, routes, and relevant travel information from external APIs.
- **Retrofit**: Handles RESTful API requests efficiently.
- **OkHttp**: Ensures stable and optimized network communication.
- **Coroutines**: Simplifies asynchronous programming.
- **LiveData & ViewModel**: Helps manage UI-related data in a lifecycle-conscious way.

## Getting Started

Follow these steps to set up and run the ITourist app locally:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Ahmed-Ashraf24/ITourist.git
