FROM python:3
RUN mkdir /results
COPY ./Client/client.py /
RUN pip3 install zeep
CMD ["python3", "/client.py"]

