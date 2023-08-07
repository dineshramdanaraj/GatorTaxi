from django.db import models

# Create your models here.
class uploadfile1(models.Model):
    fname = models.CharField(max_length= 50)
    file = models.FileField()