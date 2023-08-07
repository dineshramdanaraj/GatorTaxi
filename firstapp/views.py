from django.shortcuts import render, redirect
from django.http import HttpResponse
from .models import uploadfile1
from django.contrib import messages
import os
from django.conf import settings
from sub import process_java
# Create your views here.
def home(request):
    return render(request, 'index.html')

def uploadfile(request):
    if request.method == 'POST':

        fname = request.POST.get('fname') 
        
        file = request.FILES.get('file')
        if file:
            
            k = process_java(fname)
            if k == "success":
                print(k)
                messages.success(request, "File is uploaded successfully and processed.")
                return redirect('home')
            

def downloadfile(request):
    if request.method == "POST":
        file_name = 'output_file.txt' 
        return render(request, 'download.html', {'file_name': file_name})

def file_download(request, file_name):
  
    file_path = os.path.join(settings.STATICFILES_DIRS[0], 'output_file.txt')
    
    with open(file_path, 'rb') as file:
           
        response = HttpResponse(file.read(), content_type='application/octet-stream')
        response['Content-Disposition'] = 'attachment; filename="{}"'.format(file_name)
    return response
   
