from django import forms
class myform(forms.Form):
    fn = forms.CharField(widget=forms.TextInput(attrs={class:'form-control'}))
    file = forms.FileField(widget=forms.TextInput(attrs={class:'form-control'}))
