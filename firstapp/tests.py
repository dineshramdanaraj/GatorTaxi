from django.test import TestCase

# Create your tests here.
from sub import process_java
k = process_java("input.txt")
print(k)
