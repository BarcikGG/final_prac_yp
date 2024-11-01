-- Rental Database Creation Script

-- Create Database
CREATE DATABASE rental_db
    WITH TEMPLATE = template0 
    ENCODING = 'UTF8' 
    LOCALE_PROVIDER = libc 
    LOCALE = 'C';

-- Connect to the database
\c rental_db

-- Create Roles Table
CREATE TABLE public.roles (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL UNIQUE
);

-- Create Users Table
CREATE TABLE public.users (
                              id BIGSERIAL PRIMARY KEY,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              username VARCHAR(255) NOT NULL UNIQUE,
                              full_name VARCHAR(255) NOT NULL,
                              password VARCHAR(255) NOT NULL,
                              phone VARCHAR(255),
                              role_id BIGINT NOT NULL,
                              FOREIGN KEY (role_id) REFERENCES public.roles(id)
);

-- Create Amenities Table
CREATE TABLE public.amenities (
                                  id BIGSERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL UNIQUE,
                                  icon VARCHAR(255)
);

-- Create Apartments Table
CREATE TABLE public.apartments (
                                   id BIGSERIAL PRIMARY KEY,
                                   title VARCHAR(255) NOT NULL,
                                   description VARCHAR(255),
                                   address VARCHAR(255) NOT NULL,
                                   area DOUBLE PRECISION NOT NULL,
                                   bedrooms INTEGER NOT NULL CHECK (bedrooms >= 1),
                                   bathrooms INTEGER NOT NULL CHECK (bathrooms >= 1),
                                   price_per_night NUMERIC(38,2) NOT NULL
);

-- Create Apartment Amenities Junction Table
CREATE TABLE public.apartment_amenities (
                                            apartment_id BIGINT NOT NULL,
                                            amenity_id BIGINT NOT NULL,
                                            PRIMARY KEY (apartment_id, amenity_id),
                                            FOREIGN KEY (apartment_id) REFERENCES public.apartments(id),
                                            FOREIGN KEY (amenity_id) REFERENCES public.amenities(id)
);

-- Create Apartment Photos Table
CREATE TABLE public.apartment_photos (
                                         id BIGSERIAL PRIMARY KEY,
                                         photo_url VARCHAR(255) NOT NULL,
                                         description VARCHAR(255),
                                         apartment_id BIGINT NOT NULL,
                                         FOREIGN KEY (apartment_id) REFERENCES public.apartments(id)
);

-- Create Bookings Table
CREATE TABLE public.bookings (
                                 id BIGSERIAL PRIMARY KEY,
                                 check_in_date DATE NOT NULL,
                                 check_out_date DATE NOT NULL,
                                 status VARCHAR(255) CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),
                                 total_price NUMERIC(38,2) NOT NULL,
                                 notes VARCHAR(255),
                                 apartment_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 FOREIGN KEY (apartment_id) REFERENCES public.apartments(id),
                                 FOREIGN KEY (user_id) REFERENCES public.users(id)
);

-- Create Reviews Table
CREATE TABLE public.reviews (
                                id BIGSERIAL PRIMARY KEY,
                                rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                comment VARCHAR(1000) NOT NULL,
                                created_at TIMESTAMP WITHOUT TIME ZONE,
                                apartment_id BIGINT NOT NULL,
                                user_id BIGINT NOT NULL,
                                FOREIGN KEY (apartment_id) REFERENCES public.apartments(id),
                                FOREIGN KEY (user_id) REFERENCES public.users(id)
);