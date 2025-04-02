import pandas as pd
from sklearn.ensemble import IsolationForest

class Analyzer:
    def analyze(self, data):
        pass

class MLAnalyzer(Analyzer):
    def __init__(self):
        self.model = IsolationForest( contamination = 0.1 )

    def analyze(self, data):
        df = pd.DataFrame(data)
        anomalies = self.model.fit_predict(df[['avg(emissions)']])
        return df[anomalies == -1].to_dict('records')
