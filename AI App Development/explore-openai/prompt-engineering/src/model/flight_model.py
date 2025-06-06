from enum import Enum

from pydantic import BaseModel


class CurrencyEnum(str, Enum):
    USD = "USD"
    EUR = "EUR"
    CNY = "CNY"
    JPY = "JPY"
    SGD = "SGD"
    AUD = "AUD"
    NZD = "NZD"
    GBP = "GBP"
    INR = "INR"
    RUB = "RUB"
    # Add other currencies as needed


class FlightInfo(BaseModel):
    flight_number: str
    origin_airport_code: str
    origin_city: str
    destination_airport_code: str
    destination_city: str
    departure_time: str
    arrival_time: str


class Luggage(BaseModel):
    carry_on: int
    checked_bag: int


class TicketPrice(BaseModel):
    value: float
    currency: CurrencyEnum


class Booking(BaseModel):
    name: str
    booking_date: str
    flight_info: FlightInfo
    luggage: Luggage
    ticket_price: TicketPrice
    seat_number: str
