# GatorTaxi Ride-Sharing Service

GatorTaxi is an up-and-coming ride-sharing service that efficiently manages ride requests using a combination of a min-heap and a Red-Black Tree (RBT). The system allows users to input ride details, view ride information, and perform various operations on the ride data.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [File Input Format](#file-input-format)
- [Output](#output)
- [Technologies Used](#technologies-used)
- [Limitations](#limitations)
- [Contributing](#contributing)
- [License](#license)

## Introduction
This project implements a ride-sharing service backend in Java, utilizing a min-heap for efficient ride selection and a Red-Black Tree (RBT) for ordered storage of ride details. Additionally, a web interface using Django allows users to interact with the system, input ride details, and view the results.

## Features
1. **Print Ride Information**: Display ride details for a specific ride or a range of rides.
2. **Insert Ride**: Add a new ride with a unique ride number, estimated cost, and trip duration.
3. **Get Next Ride**: Select and display the ride with the lowest estimated cost, breaking ties by selecting the ride with the shortest trip duration.
4. **Cancel Ride**: Remove a ride from the system based on its ride number.
5. **Update Trip Duration**: Modify the trip duration of a ride, considering specific conditions and penalties.

## Installation
1. **Java Backend**: Ensure you have Java installed. Compile and run the Java code for the backend.
2. **Django Web Interface**: Install Django using `pip install django`. Navigate to the Django project directory and run the web server using `python manage.py runserver`.

## Usage
Access the web interface by visiting `http://localhost:8000` in your browser. Upload a .txt file containing ride details, perform operations, and view the results.

## File Input Format
The input file should contain ride details in the following format:
\\
Insert(rideNumber, rideCost, tripDuration)\\
Print(rideNumber)\\
Print (rideNumber1,rideNumber2)\\
UpdateTrip(rideNumber, newTripDuration)\\
GetNextRide()\\
CancelRide(rideNumber)


## Output
Results can be downloaded in .txt format or viewed directly on the web page. The output includes details of rides based on the performed operations.

## Technologies Used
- Java
- Django
- HTML
- CSS
- JavaScript

## Limitations
The system can handle a maximum of 2000 active rides.

## Contributing
Feel free to contribute to the project by submitting bug reports, feature requests, or pull requests.

## License
This project is licensed under the [MIT License](LICENSE).
