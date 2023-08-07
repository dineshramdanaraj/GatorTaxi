import subprocess


def process_java(f):
    command = f"javac GatorTaxi.java && java GatorTaxi {f}"
    subprocess.run(command, shell=True)


