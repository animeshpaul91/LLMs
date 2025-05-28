import json

from pydantic import BaseModel, ValidationError


class Person(BaseModel):
    name: str
    age: int


def parse_json(json_string: str) -> Person:
    try:
        data = json.loads(json_string)
        return Person(**data)
    except ValidationError as e:
        print("Validation error: ", e.json())
        raise e


if __name__ == "__main__":
    person_json = """
    {
    "name": "Max",
    "age": 30
    }
    """
    person: Person = parse_json(person_json)
    print(f"Person: {person}")
